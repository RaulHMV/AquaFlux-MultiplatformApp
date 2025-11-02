package utt.equipo.hackathon.data.models

import kotlinx.serialization.Serializable

/**
 * Punto de datos para gráfica
 */
@Serializable
data class ChartDataPoint(
    val timestamp: String,
    val value: Double,
    val formatted_time: String
)
