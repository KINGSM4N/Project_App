package com.example.projectapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SetupUiState(
    val gender: String = "",
    val age: Int = 28,
    val weight: Int = 75,
    val isKg: Boolean = true,
    val height: Int = 165,
    val goal: String = "",
    val activity: String = "",
    val fullName: String = "",
    val nickname: String = "",
    val email: String = "",
    val phone: String = "",
    val error: String? = null
)

class SetupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SetupUiState())
    val uiState: StateFlow<SetupUiState> = _uiState.asStateFlow()

    fun onGender(value: String) = _uiState.update { it.copy(gender = value) }
    fun onAge(value: Int) = _uiState.update { it.copy(age = value) }
    fun onWeight(value: Int) = _uiState.update { it.copy(weight = value) }
    fun onHeight(value: Int) = _uiState.update { it.copy(height = value) }
    fun onGoal(value: String) = _uiState.update { it.copy(goal = value) }
    fun onActivity(value: String) = _uiState.update { it.copy(activity = value) }

    fun onUnit(kg: Boolean) = _uiState.update {
        if (it.isKg == kg) it
        else {
            val converted = if (kg) (it.weight / 2.2046).toInt() else (it.weight * 2.2046).toInt()
            it.copy(isKg = kg, weight = converted)
        }
    }

    fun onFullName(value: String) = _uiState.update { it.copy(fullName = value, error = null) }
    fun onNickname(value: String) = _uiState.update { it.copy(nickname = value) }
    fun onEmail(value: String) = _uiState.update { it.copy(email = value, error = null) }
    fun onPhone(value: String) = _uiState.update { it.copy(phone = value) }

    fun validateProfile(): Boolean {
        val s = _uiState.value
        val ok = s.fullName.isNotBlank() && s.email.contains("@")
        if (!ok) _uiState.update { it.copy(error = "Enter your name and a valid email") }
        return ok
    }
}