package com.example.projectapp.data

import androidx.annotation.DrawableRes
import com.example.projectapp.R

data class HomeItem(
    val id: Int,
    val title: String,
    val minutes: Int = 0,
    val kcal: Int = 0,
    @DrawableRes val image: Int
)

val recommendations = listOf(
    HomeItem(1, "Squat Exercise", 12, 120, R.drawable.rec1),
    HomeItem(2, "Full Body Stretching", 12, 120, R.drawable.rec2),
    HomeItem(3, "Supplement Guide...", 12, 120, R.drawable.rec3),
    HomeItem(4, "15 Quick & Effective Daily Routines...", 12, 120, R.drawable.rec4)
)

val articles = listOf(
    HomeItem(3, "Supplement Guide...", image = R.drawable.rec1),
    HomeItem(4, "15 Quick & Effective Daily Routines...", image = R.drawable.rec2),
    HomeItem(5, "Plank With Hip Twist", image = R.drawable.rec3),
    HomeItem(6, "15 Quick & Effective Daily Routines...", image = R.drawable.rec4)
)

val weeklyChallenge = HomeItem(5, "Plank With Hip Twist", 10, 90, R.drawable.rec3)

val allItems = recommendations + articles + weeklyChallenge