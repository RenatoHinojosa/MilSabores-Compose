package com.example.milsaborescompose.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

data class UserSession(val userId: Long, val token: String, val profilePictureUri: String?)

class SessionRepository(private val context: Context) {

    private val USER_ID_KEY = longPreferencesKey("logged_in_user_id")
    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val PROFILE_PICTURE_URI_KEY = stringPreferencesKey("profile_picture_uri")

    fun getSession(): Flow<UserSession?> {
        return context.dataStore.data.map { preferences ->
            val userId = preferences[USER_ID_KEY]
            val token = preferences[TOKEN_KEY]
            val profilePictureUri = preferences[PROFILE_PICTURE_URI_KEY]
            if (userId != null && token != null) {
                UserSession(userId, token, profilePictureUri)
            } else {
                null
            }
        }
    }

    suspend fun saveSession(userId: Long, token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveProfilePicture(uri: String) {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_PICTURE_URI_KEY] = uri
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences.remove(TOKEN_KEY)
            preferences.remove(PROFILE_PICTURE_URI_KEY)
        }
    }
}