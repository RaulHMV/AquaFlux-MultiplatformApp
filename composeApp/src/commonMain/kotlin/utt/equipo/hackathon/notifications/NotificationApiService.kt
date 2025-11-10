package utt.equipo.hackathon.notifications

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import utt.equipo.hackathon.util.Result

class NotificationApiService(private val httpClient: HttpClient) {
    private val baseUrl = "https://aquaflux.vercel.app/api/users"

    suspend fun registerFCMToken(jwtToken: String, fcmToken: String): Result<Boolean> {
        return try {
            val response: HttpResponse = httpClient.put("$baseUrl/fcm-token") {
                header("Authorization", "Bearer $jwtToken")
                contentType(ContentType.Application.Json)
                setBody(mapOf("fcm_token" to fcmToken))
            }
            if (response.status.isSuccess()) {
                Result.Success(true)
            } else {
                Result.Error("Error ${response.status.value}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error desconocido", exception = e)
        }
    }

    suspend fun removeFCMToken(jwtToken: String): Result<Boolean> {
        return try {
            val response: HttpResponse = httpClient.delete("$baseUrl/fcm-token") {
                header("Authorization", "Bearer $jwtToken")
            }
            if (response.status.isSuccess()) {
                Result.Success(true)
            } else {
                Result.Error("Error ${response.status.value}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error desconocido", exception = e)
        }
    }
}
