package com.example.projectapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): MealsResponse

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealsResponse

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ): MealsResponse
}