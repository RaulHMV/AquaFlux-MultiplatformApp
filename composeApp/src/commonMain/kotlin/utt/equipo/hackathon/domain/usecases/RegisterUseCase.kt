package utt.equipo.hackathon.domain.usecases

import utt.equipo.hackathon.data.models.User
import utt.equipo.hackathon.data.repository.AuthRepository
import utt.equipo.hackathon.util.Result

/**
 * Use Case para registro de nuevos usuarios
 */
class RegisterUseCase(private val authRepository: AuthRepository) {
    
    /**
     * Ejecutar registro
     * Valida los campos antes de hacer la petición
     */
    suspend operator fun invoke(
        firstName: String,
        username: String,
        password: String
    ): Result<User> {
        // Validación de campos
        if (firstName.isBlank()) {
            return Result.Error(message = "El nombre no puede estar vacío")
        }
        
        if (username.isBlank()) {
            return Result.Error(message = "El nombre de usuario no puede estar vacío")
        }
        
        if (username.length < 3) {
            return Result.Error(message = "El nombre de usuario debe tener al menos 3 caracteres")
        }
        
        if (password.isBlank()) {
            return Result.Error(message = "La contraseña no puede estar vacía")
        }
        
        if (password.length < 6) {
            return Result.Error(message = "La contraseña debe tener al menos 6 caracteres")
        }
        
        // Ejecutar registro
        return authRepository.register(
            firstName = firstName.trim(),
            username = username.trim(),
            password = password
        )
    }
}
