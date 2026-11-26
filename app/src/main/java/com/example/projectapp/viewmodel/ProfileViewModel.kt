package com.example.projectapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectapp.data.local.AppDatabase
import com.example.projectapp.data.local.ProfileEntity
import com.example.projectapp.data.repository.ProfileRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProfileUiState(
    val fullName: String = "Madison Smith",
    val email: String = "madisons@example.com",
    val phone: String = "+123 567 89000",
    val birthDate: String = "01 / 04 / 199X",
    val birthdayLabel: String = "April 1st",
    val age: Int = 28,
    val weight: String = "75",
    val height: String = "165"
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProfileRepository(
        AppDatabase.getInstance(application).profileDao()
    )

    val uiState: StateFlow<ProfileUiState> = repository.profile
        .map { it?.toUiState() ?: ProfileUiState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ProfileUiState())

    fun createFromSetup(setup: SetupUiState) {
        viewModelScope.launch {
            val weightKg = if (setup.isKg) setup.weight else (setup.weight / 2.2046).toInt()
            repository.create(
                ProfileEntity(
                    fullName = setup.fullName,
                    nickname = setup.nickname,
                    email = setup.email,
                    phone = setup.phone,
                    birthDate = "",
                    age = setup.age,
                    weight = weightKg.toString(),
                    height = setup.height.toString(),
                    gender = setup.gender,
                    goal = setup.goal,
                    activity = setup.activity
                )
            )
        }
    }

    fun save(newState: ProfileUiState) {
        viewModelScope.launch {
            val current = repository.profile.first()
            val base = current ?: ProfileEntity(
                fullName = "", nickname = "", email = "", phone = "", birthDate = "",
                age = newState.age, weight = "", height = "",
                gender = "", goal = "", activity = ""
            )
            val updated = base.copy(
                fullName = newState.fullName,
                email = newState.email,
                phone = newState.phone,
                birthDate = newState.birthDate,
                weight = newState.weight,
                height = newState.height
            )
            if (current == null) repository.create(updated) else repository.update(updated)
        }
    }
}

private fun ProfileEntity.toUiState() = ProfileUiState(
    fullName = fullName,
    email = email,
    phone = phone,
    birthDate = birthDate,
    birthdayLabel = birthDate.ifBlank { "Not set" },
    age = age,
    weight = weight,
    height = height
)