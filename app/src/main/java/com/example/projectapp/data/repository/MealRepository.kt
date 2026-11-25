package com.example.projectapp.data.repository

import com.example.projectapp.data.Meal
import com.example.projectapp.data.remote.MealApi
import com.example.projectapp.data.remote.MealDto
import com.example.projectapp.data.remote.RetrofitClient

class MealRepository(
    private val api: MealApi = RetrofitClient.mealApi
) {

    suspend fun getMealsByCategory(category: String): Result<List<Meal>> =
        runCatching {
            api.getMealsByCategory(category).meals.orEmpty().map { it.toMeal() }
        }

    suspend fun searchMeals(query: String): Result<List<Meal>> =
        runCatching {
            api.searchMeals(query).meals.orEmpty().map { it.toMeal() }
        }

    suspend fun getMealById(id: String): Result<Meal?> =
        runCatching {
            api.getMealById(id).meals?.firstOrNull()?.toMeal()
        }
}

private fun MealDto.toMeal() = Meal(
    id = id,
    name = name,
    imageUrl = thumbnail,
    category = category,
    area = area,
    instructions = instructions
)