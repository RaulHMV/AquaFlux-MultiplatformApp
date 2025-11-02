package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

/**
 * Response de login exitoso
 */
@Serializable
data class LoginResponse(
    val user: User,
    val token: String
)
