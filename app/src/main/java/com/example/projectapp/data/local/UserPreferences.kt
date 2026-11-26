package com.example.projectapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    private val setupDoneKey = booleanPreferencesKey("setup_done")

    val setupDone: Flow<Boolean> = context.dataStore.data.map { it[setupDoneKey] ?: false }

    suspend fun setSetupDone(done: Boolean) {
        context.dataStore.edit { it[setupDoneKey] = done }
    }

    fun toggle(name: String, default: Boolean = true): Flow<Boolean> {
        val key = booleanPreferencesKey("toggle_$name")
        return context.dataStore.data.map { it[key] ?: default }
    }

    suspend fun setToggle(name: String, value: Boolean) {
        val key = booleanPreferencesKey("toggle_$name")
        context.dataStore.edit { it[key] = value }
    }
}