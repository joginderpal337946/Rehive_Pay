package com.example.rehive_pay.base.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "rehive_pay_prefs")

class DataStoreManager(private val context: Context) {
    
    companion object {
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val SPLASH_SHOWN = booleanPreferencesKey("splash_shown")
    }

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }

    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN]
    }

    suspend fun setSplashShown(shown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SPLASH_SHOWN] = shown
        }
    }

    val isSplashShown: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[SPLASH_SHOWN] ?: false
    }
    
    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
