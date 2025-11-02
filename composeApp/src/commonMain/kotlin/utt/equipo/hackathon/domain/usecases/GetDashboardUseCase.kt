package utt.equipo.hackathon.domain.usecases

import utt.equipo.hackathon.data.models.DashboardData
import utt.equipo.hackathon.data.repository.FeedsRepository
import utt.equipo.hackathon.util.Result

/**
 * Use Case para obtener datos del dashboard
 */
class GetDashboardUseCase(private val feedsRepository: FeedsRepository) {
    
    /**
     * Obtener datos completos del dashboard
     * Incluye estado de fugas y flujo de agua
     */
    suspend operator fun invoke(): Result<DashboardData> {
        return feedsRepository.getDashboard()
    }
}
