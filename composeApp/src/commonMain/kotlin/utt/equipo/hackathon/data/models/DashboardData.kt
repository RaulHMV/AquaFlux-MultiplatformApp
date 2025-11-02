package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

/**
 * Nueva estructura del Dashboard según tu API
 */
@Serializable
data class DashboardData(
    val user: User,
    val sensors: SensorsData
)
