package utt.equipo.hackathon.data.local

/**
 * Interface para almacenamiento local multiplataforma
 * Permite guardar y recuperar datos de forma segura
 */
interface LocalStorage {
    /**
     * Guardar token de autenticación
     */
    suspend fun saveAuthToken(token: String)
    
    /**
     * Obtener token de autenticación
     */
    suspend fun getAuthToken(): String?
    
    /**
     * Guardar datos de usuario
     */
    suspend fun saveUserData(
        userId: Int,
        username: String,
        firstName: String
    )
    
    /**
     * Obtener ID del usuario
     */
    suspend fun getUserId(): Int?
    
    /**
     * Obtener nombre de usuario
     */
    suspend fun getUsername(): String?
    
    /**
     * Obtener primer nombre
     */
    suspend fun getFirstName(): String?
    
    /**
     * Guardar último estado de fugas (0 o 1)
     */
    suspend fun saveLastLeakStatus(status: Int)
    
    /**
     * Obtener último estado de fugas guardado
     */
    suspend fun getLastLeakStatus(): Int?
    
    /**
     * Marcar usuario como logueado
     */
    suspend fun setLoggedIn(isLoggedIn: Boolean)
    
    /**
     * Verificar si el usuario está logueado
     */
    suspend fun isLoggedIn(): Boolean
    
    /**
     * Limpiar todos los datos (logout)
     */
    suspend fun clear()
}
