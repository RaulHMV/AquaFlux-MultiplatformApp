package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

/**
 * Request para cambiar contraseña
 */
@Serializable
data class UpdatePasswordRequest(
    val old_password: String,
    val new_password: String
)
