package utt.equipo.hackathon.data.repository

import utt.equipo.hackathon.data.local.LocalStorage
import utt.equipo.hackathon.data.local.TokenManager
import utt.equipo.hackathon.data.models.LoginResponse
import utt.equipo.hackathon.data.models.User
import utt.equipo.hackathon.data.remote.AuthApi
import utt.equipo.hackathon.util.Result

/**
 * Repositorio para operaciones de autenticación
 */
class AuthRepository(
    private val authApi: AuthApi,
    private val localStorage: LocalStorage,
    private val notificationApi: utt.equipo.hackathon.notifications.NotificationApiService? = null
) {
    
    /**
     * Login de usuario
     * Guarda el token y datos del usuario si es exitoso
     */
    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return when (val result = authApi.login(username, password)) {
            is Result.Success -> {
                println("💾 Guardando token en DataStore...")
                
                // Guardar token
                TokenManager.saveToken(result.data.token)
                println("✅ Token guardado: ${result.data.token.take(20)}...")
                
                // Guardar datos del usuario
                localStorage.saveUserData(
                    userId = result.data.user.id_user,
                    username = result.data.user.username,
                    firstName = result.data.user.first_name
                )
                println("✅ Datos de usuario guardados")
                
                // Marcar como logueado
                localStorage.setLoggedIn(true)
                println("✅ Usuario marcado como logueado")
                
                // Verificar que el token se guardó correctamente
                val savedToken = TokenManager.getToken()
                println("🔍 Token recuperado después de guardar: ${savedToken?.take(20)}...")
                
                Result.Success(result.data)
            }
            is Result.Error -> result
            is Result.Loading -> result
        }
    }
    
    /**
     * Registro de nuevo usuario
     */
    suspend fun register(
        firstName: String,
        username: String,
        password: String
    ): Result<User> {
        return authApi.register(firstName, username, password)
    }
    
    /**
     * Logout del usuario
     * Limpia todos los datos guardados
     */
    suspend fun logout() {
        // Intentar eliminar FCM token del backend antes de limpiar
        try {
            val token = TokenManager.getToken()
            if (!token.isNullOrBlank() && notificationApi != null) {
                notificationApi.removeFCMToken(token)
            }
        } catch (_: Exception) {
            // Ignorar errores al eliminar token FCM
        }
        
        TokenManager.clearToken()
        localStorage.clear()
    }
    
    /**
     * Verificar si el usuario está autenticado
     */
    suspend fun isAuthenticated(): Boolean {
        return TokenManager.hasToken() && localStorage.isLoggedIn()
    }
    
    /**
     * Obtener datos del usuario actual
     */
    suspend fun getCurrentUser(): User? {
        val userId = localStorage.getUserId() ?: return null
        val username = localStorage.getUsername() ?: return null
        val firstName = localStorage.getFirstName() ?: return null
        
        return User(
            id_user = userId,
            username = username,
            first_name = firstName,
            is_active = true
        )
    }
}
