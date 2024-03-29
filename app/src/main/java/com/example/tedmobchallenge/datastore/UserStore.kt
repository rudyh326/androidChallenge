package com.example.tedmobchallenge.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("username")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    val getUsernames: Flow<List<String>> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY]?.split(",") ?: emptyList()
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] += ",$username"
        }
    }
}