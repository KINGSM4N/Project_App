package com.example.projectapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun toggleNotif(option: NotifOption) = _uiState.update {
        it.copy(
            enabledNotifs = if (option in it.enabledNotifs) it.enabledNotifs - option
            else it.enabledNotifs + option
        )
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