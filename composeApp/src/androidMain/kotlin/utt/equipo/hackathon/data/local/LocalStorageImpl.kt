package utt.equipo.hackathon.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "aquaflux_prefs")

/**
 * Implementación de LocalStorage para Android usando DataStore
 */
class LocalStorageImpl(private val context: Context) : LocalStorage {
    
    companion object {
        private val AUTH_TOKEN = stringPreferencesKey("auth_token")
        private val USER_ID = intPreferencesKey("user_id")
        private val USERNAME = stringPreferencesKey("username")
        private val FIRST_NAME = stringPreferencesKey("first_name")
        private val LAST_LEAK_STATUS = intPreferencesKey("last_leak_status")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }
    
    override suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[AUTH_TOKEN] = token
        }
    }
    
    override suspend fun getAuthToken(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[AUTH_TOKEN]
        }.first()
    }
    
    override suspend fun saveUserData(userId: Int, username: String, firstName: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = userId
            prefs[USERNAME] = username
            prefs[FIRST_NAME] = firstName
        }
    }
    
    override suspend fun getUserId(): Int? {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID]
        }.first()
    }
    
    override suspend fun getUsername(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[USERNAME]
        }.first()
    }
    
    override suspend fun getFirstName(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[FIRST_NAME]
        }.first()
    }
    
    override suspend fun saveLastLeakStatus(status: Int) {
        context.dataStore.edit { prefs ->
            prefs[LAST_LEAK_STATUS] = status
        }
    }
    
    override suspend fun getLastLeakStatus(): Int? {
        return context.dataStore.data.map { prefs ->
            prefs[LAST_LEAK_STATUS]
        }.first()
    }
    
    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = isLoggedIn
        }
    }
    
    override suspend fun isLoggedIn(): Boolean {
        return context.dataStore.data.map { prefs ->
            prefs[IS_LOGGED_IN] ?: false
        }.first()
    }
    
    override suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
