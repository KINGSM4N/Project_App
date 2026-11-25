package com.example.projectapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val fullName: String = "Madison Smith",
    val email: String = "madisons@example.com",
    val phone: String = "+123 567 89000",
    val birthDate: String = "01 / 04 / 199X",
    val birthdayLabel: String = "April 1st",
    val age: Int = 28,
    val weight: String = "75",
    val height: String = "1.65"
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun save(newState: ProfileUiState) {
        _uiState.value = newState
    }
}