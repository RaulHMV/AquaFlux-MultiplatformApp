package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

/**
 * Request para login
 */
@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)
