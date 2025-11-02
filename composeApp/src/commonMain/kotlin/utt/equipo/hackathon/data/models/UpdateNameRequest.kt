package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

/**
 * Request para cambiar nombre de usuario
 */
@Serializable
data class UpdateNameRequest(
    val first_name: String
)
