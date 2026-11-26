package com.example.projectapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapp.data.Meal
import com.example.projectapp.data.local.AppDatabase
import com.example.projectapp.data.repository.FavoriteRepository
import com.example.projectapp.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.projectapp.data.repository.ProfileRepository

sealed interface MealsContent {
    data object Loading : MealsContent
    data class Success(
        val recommendations: List<Meal>,
        val articles: List<Meal>
    ) : MealsContent
    data class Error(val message: String) : MealsContent
    data object Empty : MealsContent
}

data class HomeUiState(
    val userName: String = "Madison",
    val content: MealsContent = MealsContent.Loading,
    val favorites: Set<String> = emptySet(),
    val selectedTab: Int = 0
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MealRepository()
    private val favoriteRepository = FavoriteRepository(
        AppDatabase.getInstance(application).favoriteDao()
    )

    private val profileRepository = ProfileRepository(
        AppDatabase.getInstance(application).profileDao()
    )

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMeals()
        observeFavorites()
        observeProfile()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoriteRepository.favorites.collect { list ->
                _uiState.update { it.copy(favorites = list.map { meal -> meal.id }.toSet()) }
            }
        }
    }

    private fun observeProfile() {
        viewModelScope.launch {
            profileRepository.profile.collect { profile ->
                val firstName = profile?.fullName?.trim()?.substringBefore(" ").orEmpty()
                if (firstName.isNotEmpty()) {
                    _uiState.update { it.copy(userName = firstName) }
                }
            }
        }
    }

    fun loadMeals() {
        _uiState.update { it.copy(content = MealsContent.Loading) }

        viewModelScope.launch {
            repository.getMealsByCategory("Seafood")
                .onSuccess { meals ->
                    val content = if (meals.isEmpty()) {
                        MealsContent.Empty
                    } else {
                        MealsContent.Success(
                            recommendations = meals.take(6),
                            articles = meals.drop(6).take(6)
                        )
                    }
                    _uiState.update { it.copy(content = content) }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(content = MealsContent.Error("Could not load data. Check your internet connection."))
                    }
                }
        }
    }

    fun toggleFavorite(meal: Meal) {
        viewModelScope.launch {
            if (meal.id in _uiState.value.favorites) {
                favoriteRepository.remove(meal.id)
            } else {
                favoriteRepository.add(meal)
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }
}