package com.example.projectapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapp.data.Meal
import com.example.projectapp.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Success(val meal: Meal) : DetailsUiState
    data class Error(val message: String) : DetailsUiState
    data object Empty : DetailsUiState
}

class DetailsViewModel(
    private val repository: MealRepository = MealRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun load(id: String) {
        _uiState.value = DetailsUiState.Loading
        viewModelScope.launch {
            repository.getMealById(id)
                .onSuccess { meal ->
                    _uiState.value =
                        if (meal == null) DetailsUiState.Empty else DetailsUiState.Success(meal)
                }
                .onFailure {
                    _uiState.value =
                        DetailsUiState.Error("Could not load data. Check your internet connection.")
                }
        }
    }
}