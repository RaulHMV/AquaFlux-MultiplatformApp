package utt.equipo.hackathon.data.local

import utt.equipo.hackathon.util.Constants

/**
 * Manager para manejar tokens JWT de forma centralizada
 */
object TokenManager {
    private var localStorage: LocalStorage? = null
    
    /**
     * Inicializar con la implementación de LocalStorage
     */
    fun init(storage: LocalStorage) {
        localStorage = storage
    }
    
    /**
     * Guardar token JWT
     */
    suspend fun saveToken(token: String) {
        localStorage?.saveAuthToken(token)
    }
    
    /**
     * Obtener token JWT actual
     */
    suspend fun getToken(): String? {
        return localStorage?.getAuthToken()
    }
    
    /**
     * Verificar si hay un token guardado
     */
    suspend fun hasToken(): Boolean {
        return !getToken().isNullOrEmpty()
    }
    
    /**
     * Limpiar token (logout)
     */
    suspend fun clearToken() {
        localStorage?.saveAuthToken("")
    }
}
