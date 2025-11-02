package utt.equipo.hackathon.domain.usecases

import utt.equipo.hackathon.data.models.LoginResponse
import utt.equipo.hackathon.data.repository.AuthRepository
import utt.equipo.hackathon.util.Result

/**
 * Use Case para login de usuarios
 */
class LoginUseCase(private val authRepository: AuthRepository) {
    
    /**
     * Ejecutar login
     * Valida los campos antes de hacer la petición
     */
    suspend operator fun invoke(username: String, password: String): Result<LoginResponse> {
        // Validación de campos
        if (username.isBlank()) {
            return Result.Error(message = "El nombre de usuario no puede estar vacío")
        }
        
        if (password.isBlank()) {
            return Result.Error(message = "La contraseña no puede estar vacía")
        }
        
        if (password.length < 6) {
            return Result.Error(message = "La contraseña debe tener al menos 6 caracteres")
        }
        
        // Ejecutar login
        return authRepository.login(username.trim(), password)
    }
}
