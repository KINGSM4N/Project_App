package com.example.projectapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projectapp.data.HomeItem
import com.example.projectapp.data.articles as defaultArticles
import com.example.projectapp.data.recommendations as defaultRecommendations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val userName: String = "Madison",
    val recommendations: List<HomeItem> = defaultRecommendations,
    val articles: List<HomeItem> = defaultArticles,
    val favorites: Set<Int> = emptySet(),
    val selectedTab: Int = 0
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun toggleFavorite(id: Int) {
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