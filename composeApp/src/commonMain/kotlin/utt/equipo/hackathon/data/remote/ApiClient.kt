package utt.equipo.hackathon.data.remote

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import utt.equipo.hackathon.util.Constants

/**
 * Cliente HTTP configurado con Ktor
 * Maneja autenticación, serialización y logging
 */
object ApiClient {
    
    /**
     * Crear cliente HTTP con configuración completa
     */
    fun create(engine: HttpClientEngine = getHttpClientEngine()): HttpClient {
        return HttpClient(engine) {
            // Configuración de JSON
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }
            
            // Configuración de logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("🌐 KTOR: $message")
                    }
                }
                level = LogLevel.ALL
            }
            
            // Configuración de timeouts
            install(HttpTimeout) {
                requestTimeoutMillis = Constants.Api.REQUEST_TIMEOUT
                connectTimeoutMillis = Constants.Api.CONNECT_TIMEOUT
                socketTimeoutMillis = Constants.Api.REQUEST_TIMEOUT
            }
            
            // Configuración por defecto de requests
            defaultRequest {
                url(Constants.Api.BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
    }
}

/**
 * Expect function para obtener el engine específico de cada plataforma
 */
expect fun getHttpClientEngine(): HttpClientEngine
