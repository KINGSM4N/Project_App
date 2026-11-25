package com.example.projectapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SetPasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val error: String? = null
)

class SetPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SetPasswordUiState())
    val uiState: StateFlow<SetPasswordUiState> = _uiState.asStateFlow()

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }

    fun onConfirmChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value, error = null) }
    }

    fun validate(): Boolean {
        val s = _uiState.value
        val message = when {
            s.password.length < 6 -> "Password must be at least 6 characters"
            s.password != s.confirmPassword -> "Passwords do not match"
            else -> null
        }
        _uiState.update { it.copy(error = message) }
        return message == null
    }
}