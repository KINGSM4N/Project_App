package com.example.projectapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projectapp.data.FavType
import com.example.projectapp.data.FavoriteItem
import com.example.projectapp.data.favoriteItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class FavFilter(val label: String) { ALL("All"), VIDEO("Video"), ARTICLE("Article") }

data class FavoritesUiState(
    val filter: FavFilter = FavFilter.ALL,
    val starred: Set<Int> = favoriteItems.map { it.id }.toSet()
) {
    val items: List<FavoriteItem>
        get() = when (filter) {
            FavFilter.ALL -> favoriteItems
            FavFilter.VIDEO -> favoriteItems.filter { it.type == FavType.VIDEO }
            FavFilter.ARTICLE -> favoriteItems.filter { it.type == FavType.ARTICLE }
        }
}

class FavoritesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    fun setFilter(filter: FavFilter) = _uiState.update { it.copy(filter = filter) }

    fun toggleStar(id: Int) = _uiState.update {
        it.copy(starred = if (id in it.starred) it.starred - id else it.starred + id)
    }
}