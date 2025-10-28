package com.example.milsaborescompose.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionManager(private val context: Context) {

    private val USER_ID_KEY = intPreferencesKey("user_id")

    suspend fun saveSession(userId: Int) {
        context.dataStore.edit {
            it[USER_ID_KEY] = userId
        }
    }

    fun getUserId(): Flow<Int?> {
        return context.dataStore.data.map {
            it[USER_ID_KEY]
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit {
            it.clear()
        }
    }
}