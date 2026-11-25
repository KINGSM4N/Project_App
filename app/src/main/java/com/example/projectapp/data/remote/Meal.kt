package com.example.projectapp.data

data class Meal(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val category: String?,
    val area: String?,
    val instructions: String?
)