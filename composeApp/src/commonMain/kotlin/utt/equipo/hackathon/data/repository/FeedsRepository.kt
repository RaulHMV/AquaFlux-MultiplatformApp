package utt.equipo.hackathon.data.repository

import utt.equipo.hackathon.data.local.LocalStorage
import utt.equipo.hackathon.data.models.ChartDataPoint
import utt.equipo.hackathon.data.models.DashboardData
import utt.equipo.hackathon.data.models.LeakDetector
import utt.equipo.hackathon.data.models.WaterFlow
import utt.equipo.hackathon.data.remote.FeedsApi
import utt.equipo.hackathon.util.Result

/**
 * Repositorio para operaciones de feeds IoT
 */
class FeedsRepository(
    private val feedsApi: FeedsApi,
    private val localStorage: LocalStorage
) {
    
    /**
     * Obtener datos completos del dashboard
     */
    suspend fun getDashboard(): Result<DashboardData> {
        return feedsApi.getDashboard()
    }
    
    /**
     * Obtener estado de fugas
     * También guarda el último estado para monitoreo
     */
    suspend fun getLeakStatus(): Result<LeakDetector> {
        return when (val result = feedsApi.getLeakStatus()) {
            is Result.Success -> {
                // Guardar último estado para comparación
                localStorage.saveLastLeakStatus(result.data.lastValue)
                Result.Success(result.data)
            }
            is Result.Error -> result
            is Result.Loading -> result
        }
    }
    
    /**
     * Obtener datos de flujo de agua
     */
    suspend fun getWaterFlow(): Result<WaterFlow> {
        return feedsApi.getWaterFlow()
    }
    
    /**
     * Obtener datos para gráfica
     * @param hours Número de horas hacia atrás (default: 24, max: 720)
     */
    suspend fun getChartData(hours: Int = 24): Result<List<ChartDataPoint>> {
        return feedsApi.getChartData(hours)
    }
    
    /**
     * Obtener último estado de fugas guardado localmente
     */
    suspend fun getLastLeakStatus(): Int? {
        return localStorage.getLastLeakStatus()
    }
}
