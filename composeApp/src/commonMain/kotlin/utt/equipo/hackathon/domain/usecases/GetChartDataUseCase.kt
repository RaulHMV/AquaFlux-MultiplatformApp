package utt.equipo.hackathon.domain.usecases

import utt.equipo.hackathon.data.models.ChartDataPoint
import utt.equipo.hackathon.data.repository.FeedsRepository
import utt.equipo.hackathon.util.Result

/**
 * Use Case para obtener datos de gráfica de consumo
 */
class GetChartDataUseCase(private val feedsRepository: FeedsRepository) {
    
    /**
     * Obtener datos para gráfica
     * @param hours Número de horas hacia atrás (default: 24, max: 720)
     */
    suspend operator fun invoke(hours: Int = 24): Result<List<ChartDataPoint>> {
        // Validar rango de horas
        val validHours = when {
            hours < 1 -> 1
            hours > 720 -> 720
            else -> hours
        }
        
        return feedsRepository.getChartData(validHours)
    }
}
