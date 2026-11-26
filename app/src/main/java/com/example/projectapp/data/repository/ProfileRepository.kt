package com.example.projectapp.data.repository

import com.example.projectapp.data.local.ProfileDao
import com.example.projectapp.data.local.ProfileEntity
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val dao: ProfileDao) {

    val profile: Flow<ProfileEntity?> = dao.observe()

    suspend fun create(profile: ProfileEntity) = dao.insert(profile)

    suspend fun update(profile: ProfileEntity) = dao.update(profile)

    suspend fun delete() = dao.clear()
}