package utt.equipo.hackathon.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Modelo de usuario
 */
@Serializable
data class User(
    @SerialName("id_user") val id_user: Int,
    val first_name: String,
    val username: String,
    val is_active: Boolean
)
