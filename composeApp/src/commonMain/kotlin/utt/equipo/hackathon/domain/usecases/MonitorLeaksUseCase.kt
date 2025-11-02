package utt.equipo.hackathon.domain.usecases

import utt.equipo.hackathon.data.repository.FeedsRepository
import kotlinx.coroutines.delay
import utt.equipo.hackathon.util.Constants
import utt.equipo.hackathon.util.Result

/**
 * Use Case para monitorear fugas en background
 * Verifica cada 30 segundos si hay cambios en el estado de fugas
 */
class MonitorLeaksUseCase(
    private val feedsRepository: FeedsRepository,
    private val onLeakDetected: suspend (liters: Double) -> Unit,
    private val onLeakResolved: suspend () -> Unit
) {
    
    /**
     * Iniciar monitoreo continuo
     * Esta función debe ejecutarse en un coroutine de background
     */
    suspend fun startMonitoring() {
        while (true) {
            checkForLeaks()
            delay(Constants.Monitoring.LEAK_CHECK_INTERVAL)
        }
    }
    
    /**
     * Verificar una sola vez si hay cambios en fugas
     */
    suspend fun checkForLeaks() {
        // Obtener estado actual de fugas
        val currentStatusResult = feedsRepository.getLeakStatus()
        
        if (currentStatusResult is Result.Success) {
            val currentStatus = currentStatusResult.data
            val previousStatus = feedsRepository.getLastLeakStatus()
            
            // Detectar cambio de estado
            if (previousStatus != null && previousStatus != currentStatus.lastValue) {
                when {
                    // Nueva fuga detectada (0 → 1)
                    currentStatus.lastValue == 1 && previousStatus == 0 -> {
                        // Obtener litros fugados
                        val waterFlowResult = feedsRepository.getWaterFlow()
                        val liters = waterFlowResult.getOrNull()?.liters ?: 0.0
                        
                        // Notificar fuga detectada
                        onLeakDetected(liters)
                    }
                    
                    // Fuga solucionada (1 → 0)
                    currentStatus.lastValue == 0 && previousStatus == 1 -> {
                        // Notificar fuga resuelta
                        onLeakResolved()
                    }
                }
            }
        }
    }
}
