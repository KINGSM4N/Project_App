package com.example.projectapp.data.remote

import com.google.gson.annotations.SerializedName

data class MealsResponse(
    val meals: List<MealDto>?
)

data class MealDto(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val thumbnail: String?,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strArea") val area: String?,
    @SerializedName("strInstructions") val instructions: String?
)