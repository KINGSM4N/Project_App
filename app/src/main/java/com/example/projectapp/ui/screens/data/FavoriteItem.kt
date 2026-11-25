package com.example.projectapp.data

import androidx.annotation.DrawableRes
import com.example.projectapp.R

enum class FavType { VIDEO, ARTICLE }

data class FavoriteItem(
    val id: Int,
    val title: String,
    val type: FavType,
    val minutes: Int = 0,
    val kcal: Int = 0,
    val exercises: Int = 0,
    val description: String = "",
    @DrawableRes val image: Int
)

val favoriteItems = listOf(
    FavoriteItem(101, "Upper Body", FavType.VIDEO, 60, 1320, 5, image = R.drawable.rec1),
    FavoriteItem(102, "Boost Energy And Vitality", FavType.ARTICLE,
        description = "Incorporating physical exercise into your daily routine can boost...", image = R.drawable.rec2),
    FavoriteItem(103, "Pull Out", FavType.VIDEO, 30, 1210, 10, image = R.drawable.rec3),
    FavoriteItem(104, "Lower Body Blast", FavType.ARTICLE,
        description = "A lower body blast is a high-intensity workout focused on targeting...", image = R.drawable.rec4),
    FavoriteItem(105, "Avocado And Egg Toast", FavType.ARTICLE, 15, 150, image = R.drawable.rec5),
    FavoriteItem(106, "Loop Band Exercises", FavType.VIDEO, 45, 785, 5, image = R.drawable.rec1),
    FavoriteItem(107, "Dumbbell Step Up", FavType.VIDEO, 12, 1385, 3, image = R.drawable.rec2),
    FavoriteItem(108, "Split Strength Training", FavType.VIDEO, 12, 1250, 5, image = R.drawable.rec3),
    FavoriteItem(109, "Fruit Smoothie", FavType.ARTICLE, 12, 120, image = R.drawable.rec4),
    FavoriteItem(110, "Hydrate Properly", FavType.ARTICLE,
        description = "Stay hydrated before, during, and after your workouts to optimize...", image = R.drawable.rec5),
    FavoriteItem(111, "Core Burner", FavType.VIDEO, 20, 540, 6, image = R.drawable.rec2),
    FavoriteItem(112, "Protein Breakfast Ideas", FavType.ARTICLE,
        description = "Start your morning with high-protein meals that keep you full and...", image = R.drawable.rec1)
)