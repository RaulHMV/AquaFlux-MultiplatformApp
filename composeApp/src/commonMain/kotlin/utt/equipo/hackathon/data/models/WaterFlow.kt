package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

@Serializable
data class WaterFlow(
    val liters: Double,
    val timestamp: String,
    val unit: String
)
