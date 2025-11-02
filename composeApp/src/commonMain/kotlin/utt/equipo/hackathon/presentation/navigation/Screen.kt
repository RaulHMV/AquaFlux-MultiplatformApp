package utt.equipo.hackathon.presentation.navigation

/**
 * Rutas de navegación de la aplicación AquaFlux
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Dashboard : Screen("dashboard")
    data object Menu : Screen("menu")
}
