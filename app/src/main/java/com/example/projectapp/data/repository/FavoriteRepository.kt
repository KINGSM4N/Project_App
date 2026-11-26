package com.example.projectapp.data.repository

import com.example.projectapp.data.Meal
import com.example.projectapp.data.local.FavoriteDao
import com.example.projectapp.data.local.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(
    private val dao: FavoriteDao
) {

    val favorites: Flow<List<Meal>> =
        dao.getAll().map { list -> list.map { it.toMeal() } }

    suspend fun add(meal: Meal) {
        dao.insert(meal.toEntity())
    }

    suspend fun remove(id: String) {
        dao.deleteById(id)
    }
}

private fun FavoriteEntity.toMeal() = Meal(
    id = id,
    name = name,
    imageUrl = imageUrl,
    category = category,
    area = area,
    instructions = null
)

private fun Meal.toEntity() = FavoriteEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    category = category,
    area = area
)