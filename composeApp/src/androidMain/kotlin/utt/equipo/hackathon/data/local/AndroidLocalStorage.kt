package utt.equipo.hackathon.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import utt.equipo.hackathon.util.Constants

/**
 * Extension property para DataStore
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.Storage.PREFERENCES_NAME
)

/**
 * Implementación de LocalStorage usando DataStore para Android
 */
class AndroidLocalStorage(private val context: Context) : LocalStorage {
    
    companion object {
        private val KEY_AUTH_TOKEN = stringPreferencesKey(Constants.Storage.KEY_AUTH_TOKEN)
        private val KEY_USER_ID = intPreferencesKey(Constants.Storage.KEY_USER_ID)
        private val KEY_USERNAME = stringPreferencesKey(Constants.Storage.KEY_USER_NAME)
        private val KEY_FIRST_NAME = stringPreferencesKey(Constants.Storage.KEY_FIRST_NAME)
        private val KEY_LAST_LEAK_STATUS = intPreferencesKey(Constants.Storage.KEY_LAST_LEAK_STATUS)
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey(Constants.Storage.KEY_IS_LOGGED_IN)
    }
    
    override suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
        }
    }
    
    override suspend fun getAuthToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_AUTH_TOKEN]
        }.first()
    }
    
    override suspend fun saveUserData(userId: Int, username: String, firstName: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
            preferences[KEY_USERNAME] = username
            preferences[KEY_FIRST_NAME] = firstName
        }
    }
    
    override suspend fun getUserId(): Int? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_USER_ID]
        }.first()
    }
    
    override suspend fun getUsername(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_USERNAME]
        }.first()
    }
    
    override suspend fun getFirstName(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_FIRST_NAME]
        }.first()
    }
    
    override suspend fun saveLastLeakStatus(status: Int) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LAST_LEAK_STATUS] = status
        }
    }
    
    override suspend fun getLastLeakStatus(): Int? {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_LAST_LEAK_STATUS]
        }.first()
    }
    
    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_IS_LOGGED_IN] = isLoggedIn
        }
    }
    
    override suspend fun isLoggedIn(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_IS_LOGGED_IN] ?: false
        }.first()
    }
    
    override suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
