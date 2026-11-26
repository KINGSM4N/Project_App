package com.example.projectapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapp.data.local.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class NotifOption(val label: String) {
    GENERAL("General Notification"),
    SOUND("Sound"),
    DND("Don't Disturb Mode"),
    VIBRATE("Vibrate"),
    LOCK_SCREEN("Lock Screen"),
    REMINDERS("Reminders")
}

data class SettingsUiState(
    val enabledNotifs: Set<NotifOption> = setOf(NotifOption.VIBRATE, NotifOption.LOCK_SCREEN),
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val passwordError: String? = null,
    val passwordChanged: Boolean = false
)

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)
    private val defaultEnabled = setOf(NotifOption.VIBRATE, NotifOption.LOCK_SCREEN)

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                NotifOption.entries.map { option ->
                    prefs.toggle(option.name, default = option in defaultEnabled)
                        .map { enabled -> option to enabled }
                }
            ) { pairs ->
                pairs.filter { it.second }.map { it.first }.toSet()
            }.collect { enabled ->
                _uiState.update { it.copy(enabledNotifs = enabled) }
            }
        }
    }

    fun toggleNotif(option: NotifOption) {
        viewModelScope.launch {
            val isOn = option in _uiState.value.enabledNotifs
            prefs.setToggle(option.name, !isOn)
        }
    }

    fun onCurrentPassword(v: String) = _uiState.update { it.copy(currentPassword = v, passwordError = null) }
    fun onNewPassword(v: String) = _uiState.update { it.copy(newPassword = v, passwordError = null) }
    fun onConfirmPassword(v: String) = _uiState.update { it.copy(confirmPassword = v, passwordError = null) }

    fun changePassword() = _uiState.update {
        val error = when {
            it.currentPassword.isBlank() -> "Enter your current password"
            it.newPassword.length < 8 -> "New password must be at least 8 characters"
            it.newPassword != it.confirmPassword -> "Passwords do not match"
            else -> null
        }
        if (error != null) it.copy(passwordError = error)
        else it.copy(
            currentPassword = "", newPassword = "", confirmPassword = "",
            passwordError = null, passwordChanged = true
        )
    }

    fun passwordChangeHandled() = _uiState.update { it.copy(passwordChanged = false) }
}