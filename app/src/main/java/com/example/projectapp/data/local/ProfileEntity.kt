package com.example.projectapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val fullName: String,
    val nickname: String,
    val email: String,
    val phone: String,
    val birthDate: String,
    val age: Int,
    val weight: String,
    val height: String,
    val gender: String,
    val goal: String,
    val activity: String
)