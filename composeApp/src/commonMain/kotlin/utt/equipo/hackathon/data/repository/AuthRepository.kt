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
    private val localStorage: LocalStorage
) {
    
    /**
     * Login de usuario
     * Guarda el token y datos del usuario si es exitoso
     */
    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return when (val result = authApi.login(username, password)) {
            is Result.Success -> {
                // Guardar token
                TokenManager.saveToken(result.data.token)
                
                // Guardar datos del usuario
                localStorage.saveUserData(
                    userId = result.data.user.id_user,
                    username = result.data.user.username,
                    firstName = result.data.user.first_name
                )
                
                // Marcar como logueado
                localStorage.setLoggedIn(true)
                
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
