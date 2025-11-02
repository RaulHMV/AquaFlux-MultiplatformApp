package utt.equipo.hackathon.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import utt.equipo.hackathon.di.DependencyContainer
import utt.equipo.hackathon.presentation.ui.theme.AquaFluxColors

/**
 * Splash Screen - Pantalla de carga inicial
 */
@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    var isCheckingAuth by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(2000)
        
        val isAuthenticated = DependencyContainer.authRepository.isAuthenticated()
        
        if (isAuthenticated) {
            onNavigateToDashboard()
        } else {
            onNavigateToLogin()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AquaFluxColors.Negro),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "AQUAFLUX",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = AquaFluxColors.AmarilloClaro,
                letterSpacing = 4.sp
            )
            
            if (isCheckingAuth) {
                CircularProgressIndicator(
                    color = AquaFluxColors.AmarilloClaro,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
