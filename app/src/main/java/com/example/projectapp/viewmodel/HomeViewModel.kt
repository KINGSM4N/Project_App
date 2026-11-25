package com.example.projectapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapp.data.Meal
import com.example.projectapp.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

class HomeViewModel(
    private val repository: MealRepository = MealRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMeals()
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

    fun toggleFavorite(id: String) {
        _uiState.update {
            val newFavorites =
                if (id in it.favorites) it.favorites - id else it.favorites + id
            it.copy(favorites = newFavorites)
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }
}