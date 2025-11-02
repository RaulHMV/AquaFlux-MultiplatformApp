package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SensorsData(
    val leakDetector: LeakDetector,
    val waterFlow: WaterFlow,
    val timestamp: String
)
