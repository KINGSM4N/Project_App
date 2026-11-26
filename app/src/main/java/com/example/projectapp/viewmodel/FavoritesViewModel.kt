package com.example.projectapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapp.data.Meal
import com.example.projectapp.data.local.AppDatabase
import com.example.projectapp.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class FavFilter(val label: String) { ALL("All"), AZ("A-Z"), ZA("Z-A") }

data class FavoritesUiState(
    val filter: FavFilter = FavFilter.ALL,
    val items: List<Meal> = emptyList(),
    val isLoading: Boolean = true
)

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FavoriteRepository(
        AppDatabase.getInstance(application).favoriteDao()
    )

    private val filter = MutableStateFlow(FavFilter.ALL)

    val uiState: StateFlow<FavoritesUiState> =
        combine(repository.favorites, filter) { list, selected ->
            val sorted = when (selected) {
                FavFilter.ALL -> list
                FavFilter.AZ -> list.sortedBy { it.name.lowercase() }
                FavFilter.ZA -> list.sortedByDescending { it.name.lowercase() }
            }
            FavoritesUiState(filter = selected, items = sorted, isLoading = false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState()
        )

    fun setFilter(newFilter: FavFilter) {
        filter.value = newFilter
    }

    fun remove(id: String) {
        viewModelScope.launch { repository.remove(id) }
    }
}