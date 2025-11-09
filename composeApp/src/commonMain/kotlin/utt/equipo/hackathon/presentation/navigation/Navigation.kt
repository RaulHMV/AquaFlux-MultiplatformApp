package utt.equipo.hackathon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import utt.equipo.hackathon.presentation.ui.screens.*
import utt.equipo.hackathon.presentation.viewmodel.DashboardViewModel
import utt.equipo.hackathon.presentation.viewmodel.LoginViewModel
import utt.equipo.hackathon.presentation.viewmodel.RegisterViewModel

/**
 * Configuración de navegación principal de la aplicación
 */
@Composable
fun AquaFluxNavigation(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    dashboardViewModel: DashboardViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Login Screen
        composable(Screen.Login.route) {
            val uiState by loginViewModel.uiState.collectAsState()
            
            LoginScreen(
                uiState = uiState,
                onLoginClick = { username, password ->
                    loginViewModel.login(username, password)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Register Screen
        composable(Screen.Register.route) {
            val uiState by registerViewModel.uiState.collectAsState()
            
            RegisterScreen(
                uiState = uiState,
                onRegisterClick = { firstName, apellido, username, password ->
                    registerViewModel.register(firstName, apellido, username, password)
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }
        
        // Dashboard Screen
        composable(Screen.Dashboard.route) {
            val uiState by dashboardViewModel.uiState.collectAsState()
            val chartState by dashboardViewModel.chartState.collectAsState()
            val selectedTimeFilter by dashboardViewModel.selectedTimeFilter.collectAsState()
            
            DashboardScreen(
                uiState = uiState,
                chartState = chartState,
                selectedTimeFilter = selectedTimeFilter,
                onMenuClick = {
                    navController.navigate(Screen.Menu.route)
                },
                onRefresh = {
                    dashboardViewModel.refresh()
                },
                onTimeFilterChange = { filter ->
                    dashboardViewModel.setTimeFilter(filter)
                }
            )
        }
        
        // Menu Screen
        composable(Screen.Menu.route) {
            MenuScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
