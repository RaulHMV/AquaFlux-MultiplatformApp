package utt.equipo.hackathon.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import utt.equipo.hackathon.data.models.*
import utt.equipo.hackathon.util.Constants
import utt.equipo.hackathon.util.Result
import utt.equipo.hackathon.util.resultOf

/**
 * API Service para endpoints de autenticación
 */
class AuthApi(private val client: HttpClient) {
    
    /**
     * Login de usuario
     * POST /auth/login
     */
    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            println("🔐 LOGIN: Intentando login con username: $username")
            println("🔐 URL: ${Constants.Api.BASE_URL}${Constants.Api.LOGIN}")
            
            val response = client.post(Constants.Api.LOGIN) {
                setBody(LoginRequest(username, password))
            }
            
            println("🔐 RESPONSE STATUS: ${response.status}")
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    val apiResponse = response.body<ApiResponse<LoginResponse>>()
                    println("✅ LOGIN EXITOSO: Token recibido")
                    Result.Success(apiResponse.data)
                }
                HttpStatusCode.Unauthorized -> {
                    println("❌ LOGIN FALLIDO: Credenciales inválidas")
                    Result.Error(
                        message = Constants.ErrorMessages.INVALID_CREDENTIALS,
                        code = response.status.value
                    )
                }
                HttpStatusCode.BadRequest -> {
                    val errorBody = try {
                        response.body<ApiResponse<Unit>>()
                    } catch (e: Exception) {
                        null
                    }
                    println("❌ LOGIN FALLIDO: Bad Request - ${errorBody?.message}")
                    Result.Error(
                        message = errorBody?.message ?: "Datos inválidos",
                        code = response.status.value
                    )
                }
                else -> {
                    println("❌ LOGIN FALLIDO: Status ${response.status.value}")
                    Result.Error(
                        message = Constants.ErrorMessages.SERVER_ERROR,
                        code = response.status.value
                    )
                }
            }
        } catch (e: Exception) {
            println("❌ LOGIN ERROR: ${e.message}")
            e.printStackTrace()
            handleException(e)
        }
    }
    
    /**
     * Registro de nuevo usuario
     * POST /auth/register
     */
    suspend fun register(
        firstName: String,
        username: String,
        password: String
    ): Result<User> {
        return try {
            val response = client.post(Constants.Api.REGISTER) {
                setBody(RegisterRequest(
                    first_name = firstName,
                    username = username,
                    password = password
                ))
            }
            
            when (response.status) {
                HttpStatusCode.Created -> {
                    val apiResponse = response.body<ApiResponse<User>>()
                    Result.Success(apiResponse.data)
                }
                HttpStatusCode.BadRequest -> {
                    val errorBody = try {
                        response.body<ApiResponse<Unit>>()
                    } catch (e: Exception) {
                        null
                    }
                    Result.Error(
                        message = errorBody?.message ?: "El usuario ya existe o datos inválidos",
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
        println("🔥 EXCEPTION DETAILS:")
        println("   Type: ${e::class.simpleName}")
        println("   Message: ${e.message}")
        
        return when {
            e.message?.contains("timeout", ignoreCase = true) == true -> {
                Result.Error(
                    message = "La solicitud tardó demasiado. Verifica tu conexión.",
                    exception = e
                )
            }
            e.message?.contains("Unable to resolve host", ignoreCase = true) == true ||
            e.message?.contains("Failed to connect", ignoreCase = true) == true ||
            e.message?.contains("Connection refused", ignoreCase = true) == true -> {
                Result.Error(
                    message = "No se pudo conectar al servidor. Verifica que esté corriendo en puerto 3000.",
                    exception = e
                )
            }
            e.message?.contains("JSON", ignoreCase = true) == true ||
            e.message?.contains("Serialization", ignoreCase = true) == true -> {
                Result.Error(
                    message = "Error al procesar la respuesta del servidor. Formato incorrecto.",
                    exception = e
                )
            }
            else -> {
                Result.Error(
                    message = "Error: ${e.message ?: "Desconocido"}",
                    exception = e
                )
            }
        }
    }
}
