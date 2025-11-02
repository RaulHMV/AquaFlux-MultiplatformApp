package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LeakDetector(
    val hasLeak: Boolean,
    val lastValue: Int,
    val timestamp: String,
    val message: String
)
