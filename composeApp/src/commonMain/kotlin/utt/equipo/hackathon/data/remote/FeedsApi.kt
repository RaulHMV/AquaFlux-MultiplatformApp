package utt.equipo.hackathon.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import utt.equipo.hackathon.data.models.*
import utt.equipo.hackathon.util.Constants
import utt.equipo.hackathon.util.Result

/**
 * API Service para endpoints de feeds IoT
 */
class FeedsApi(private val client: HttpClient) {
    
    /**
     * Obtener datos completos del dashboard
     * GET /feeds/dashboard
     */
    suspend fun getDashboard(): Result<DashboardData> {
        return try {
            val token = utt.equipo.hackathon.data.local.TokenManager.getToken()
            println("🔐 FeedsApi.getDashboard - Token disponible: ${token?.take(20)}...")
            
            val response = client.get(Constants.Api.DASHBOARD) {
                if (token != null) {
                    header("Authorization", "Bearer $token")
                    println("✅ Header Authorization agregado manualmente")
                }
            }
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    val apiResponse = response.body<ApiResponse<DashboardData>>()
                    Result.Success(apiResponse.data)
                }
                HttpStatusCode.Unauthorized -> {
                    Result.Error(
                        message = Constants.ErrorMessages.UNAUTHORIZED,
                        code = response.status.value
                    )
                }
                else -> {
                    Result.Error(
                        message = Constants.ErrorMessages.SERVER_ERROR,
                        code = response.status.value
                    )
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    /**
     * Obtener estado de fugas
     * GET /feeds/leak-status
     */
    suspend fun getLeakStatus(): Result<LeakDetector> {
        return try {
            val token = utt.equipo.hackathon.data.local.TokenManager.getToken()
            
            val response = client.get(Constants.Api.LEAK_STATUS) {
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    val apiResponse = response.body<ApiResponse<LeakDetector>>()
                    Result.Success(apiResponse.data)
                }
                HttpStatusCode.Unauthorized -> {
                    Result.Error(
                        message = Constants.ErrorMessages.UNAUTHORIZED,
                        code = response.status.value
                    )
                }
                else -> {
                    Result.Error(
                        message = Constants.ErrorMessages.SERVER_ERROR,
                        code = response.status.value
                    )
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    /**
     * Obtener datos de flujo de agua
     * GET /feeds/water-flow
     */
    suspend fun getWaterFlow(): Result<WaterFlow> {
        return try {
            val token = utt.equipo.hackathon.data.local.TokenManager.getToken()
            
            val response = client.get(Constants.Api.WATER_FLOW) {
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    val apiResponse = response.body<ApiResponse<WaterFlow>>()
                    Result.Success(apiResponse.data)
                }
                HttpStatusCode.Unauthorized -> {
                    Result.Error(
                        message = Constants.ErrorMessages.UNAUTHORIZED,
                        code = response.status.value
                    )
                }
                else -> {
                    Result.Error(
                        message = Constants.ErrorMessages.SERVER_ERROR,
                        code = response.status.value
                    )
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    /**
     * Obtener datos para gráfica
     * GET /feeds/water-flow-chart?hours=24
     */
    suspend fun getChartData(hours: Int = 24): Result<List<ChartDataPoint>> {
        return try {
            val token = utt.equipo.hackathon.data.local.TokenManager.getToken()
            
            val response = client.get(Constants.Api.WATER_FLOW_CHART) {
                parameter("hours", hours)
                if (token != null) {
                    header("Authorization", "Bearer $token")
                }
            }
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    val apiResponse = response.body<ApiResponse<List<ChartDataPoint>>>()
                    Result.Success(apiResponse.data)
                }
                HttpStatusCode.Unauthorized -> {
                    Result.Error(
                        message = Constants.ErrorMessages.UNAUTHORIZED,
                        code = response.status.value
                    )
                }
                else -> {
                    Result.Error(
                        message = Constants.ErrorMessages.SERVER_ERROR,
                        code = response.status.value
                    )
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }
    
    /**
     * Manejo de excepciones comunes
     */
    private fun handleException(e: Exception): Result.Error {
        return when {
            e.message?.contains("timeout", ignoreCase = true) == true -> {
                Result.Error(
                    message = Constants.ErrorMessages.TIMEOUT_ERROR,
                    exception = e
                )
            }
            e.message?.contains("Unable to resolve host", ignoreCase = true) == true ||
            e.message?.contains("Failed to connect", ignoreCase = true) == true -> {
                Result.Error(
                    message = Constants.ErrorMessages.NETWORK_ERROR,
                    exception = e
                )
            }
            else -> {
                Result.Error(
                    message = e.message ?: Constants.ErrorMessages.UNKNOWN_ERROR,
                    exception = e
                )
            }
        }
    }
}
