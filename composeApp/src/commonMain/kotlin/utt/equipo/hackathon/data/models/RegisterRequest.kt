package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

/**
 * Request para registro
 */
@Serializable
data class RegisterRequest(
    val first_name: String,
    val username: String,
    val password: String
)
