package utt.equipo.hackathon

import androidx.compose.runtime.Composable
import utt.equipo.hackathon.di.DependencyContainer
import utt.equipo.hackathon.presentation.navigation.AquaFluxNavigation
import utt.equipo.hackathon.presentation.ui.theme.AquaFluxTheme
import utt.equipo.hackathon.presentation.viewmodel.DashboardViewModel
import utt.equipo.hackathon.presentation.viewmodel.LoginViewModel
import utt.equipo.hackathon.presentation.viewmodel.RegisterViewModel

/**
 * Punto de entrada principal de la aplicación AquaFlux
 */
@Composable
fun App(
    notificationManager: utt.equipo.hackathon.notifications.NotificationManager? = null,
    notificationApiService: utt.equipo.hackathon.notifications.NotificationApiService? = null
) {
    // Inicializar ViewModels
    val loginViewModel = LoginViewModel(
        loginUseCase = DependencyContainer.loginUseCase
    )
    
    val registerViewModel = RegisterViewModel(
        registerUseCase = DependencyContainer.registerUseCase
    )
    
    val dashboardViewModel = DashboardViewModel(
        getDashboardUseCase = DependencyContainer.getDashboardUseCase,
        getChartDataUseCase = DependencyContainer.getChartDataUseCase,
        notificationManager = notificationManager,
        notificationApi = notificationApiService ?: DependencyContainer.notificationApiService
    )
    
    // Aplicar tema y navegación
    AquaFluxTheme {
        AquaFluxNavigation(
            loginViewModel = loginViewModel,
            registerViewModel = registerViewModel,
            dashboardViewModel = dashboardViewModel
        )
    }
}
