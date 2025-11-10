package utt.equipo.hackathon.di

import utt.equipo.hackathon.data.local.LocalStorage
import utt.equipo.hackathon.data.local.TokenManager
import utt.equipo.hackathon.data.remote.ApiClient
import utt.equipo.hackathon.data.remote.AuthApi
import utt.equipo.hackathon.data.remote.FeedsApi
import utt.equipo.hackathon.data.remote.getHttpClientEngine
import utt.equipo.hackathon.data.repository.AuthRepository
import utt.equipo.hackathon.data.repository.FeedsRepository
import utt.equipo.hackathon.domain.usecases.*

/**
 * Dependency Injection Container
 * Proporciona todas las dependencias de la aplicación
 */
object DependencyContainer {
    
    private lateinit var _localStorage: LocalStorage
    
    /**
     * Inicializar el contenedor con la implementación de LocalStorage
     * DEBE ser llamado al iniciar la aplicación
     */
    fun init(localStorage: LocalStorage) {
        _localStorage = localStorage
        TokenManager.init(localStorage)
    }
    
    // HTTP Client
    private val httpClient by lazy {
        ApiClient.create(getHttpClientEngine())
    }
    
    // API Services
    private val authApi by lazy {
        AuthApi(httpClient)
    }
    
    private val feedsApi by lazy {
        FeedsApi(httpClient)
    }

    // Notification API service
    val notificationApiService by lazy {
        utt.equipo.hackathon.notifications.NotificationApiService(httpClient)
    }
    
    // Repositories
    val authRepository by lazy {
        AuthRepository(authApi, _localStorage, notificationApiService)
    }
    
    val feedsRepository by lazy {
        FeedsRepository(feedsApi, _localStorage)
    }
    
    // Use Cases
    val loginUseCase by lazy {
        LoginUseCase(authRepository)
    }
    
    val registerUseCase by lazy {
        RegisterUseCase(authRepository)
    }
    
    val getDashboardUseCase by lazy {
        GetDashboardUseCase(feedsRepository)
    }
    
    val getChartDataUseCase by lazy {
        GetChartDataUseCase(feedsRepository)
    }
    
    /**
     * Crear MonitorLeaksUseCase con callbacks personalizados
     */
    fun createMonitorLeaksUseCase(
        onLeakDetected: suspend (Double) -> Unit,
        onLeakResolved: suspend () -> Unit
    ): MonitorLeaksUseCase {
        return MonitorLeaksUseCase(
            feedsRepository = feedsRepository,
            onLeakDetected = onLeakDetected,
            onLeakResolved = onLeakResolved
        )
    }
}
