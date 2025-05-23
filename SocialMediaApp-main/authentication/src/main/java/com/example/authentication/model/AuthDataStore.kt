package com.example.authentication.model

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val Context.dataStore by preferencesDataStore(name = "auth_prefs")

    fun getValue(key: Preferences.Key<String>): Flow<String?> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }
            .map { preferences -> preferences[key] }

//    private val username: Flow<String?> = context.dataStore.data
//        .catch { exception ->
//            if (exception is IOException) emit(emptyPreferences()) else throw exception
//        }
//        .map { preferences -> preferences[USERNAME] }
//
//    private val email: Flow<String?> = context.dataStore.data
//        .catch { exception ->
//            if (exception is IOException) emit(emptyPreferences()) else throw exception
//        }
//        .map { preferences -> preferences[EMAIL] }
//
//    private val displayName: Flow<String?> = context.dataStore.data
//        .catch { exception ->
//            if (exception is IOException) emit(emptyPreferences()) else throw exception
//        }
//        .map { preferences -> preferences[DISPLAY_NAME] }
//
//    private val authToken: Flow<String?> = context.dataStore.data
//        .catch { exception ->
//            if (exception is IOException) emit(emptyPreferences()) else throw exception
//        }
//        .map { preferences -> preferences[TOKEN_KEY] }

    suspend fun setValue(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

//    suspend fun saveAuthToken(token: String) {
//        context.dataStore.edit { preferences ->
//            preferences[TOKEN_KEY] = token
//        }
//    }
//
//    suspend fun saveUsername(username: String) {
//        context.dataStore.edit { preferences ->
//            preferences[USERNAME] = username
//        }
//    }
//
//    suspend fun getUsername(): String? = username.firstOrNull()
//
//    suspend fun clearAuthToken() {
//        context.dataStore.edit { preferences ->
//            preferences.remove(TOKEN_KEY)
//        }
//    }
//
    suspend fun isUserLoggedIn(): Boolean {
        return getValue(KEY_TOKEN).firstOrNull() != null
    }

    suspend fun clearValue(key: Preferences.Key<String>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    companion object {
        val KEY_USERNAME = stringPreferencesKey("username")
        val KEY_EMAIL = stringPreferencesKey("email")
        val KEY_DISPLAY_NAME = stringPreferencesKey("display_name")
        val KEY_BIO = stringPreferencesKey("bio")
        val KEY_PROFILE_IMAGE_URL = stringPreferencesKey("profile_image_url")
        val KEY_TOKEN = stringPreferencesKey("auth_token")
    }
}