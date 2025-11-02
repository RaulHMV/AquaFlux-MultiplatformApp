package utt.equipo.hackathon.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AquaFluxColorScheme = darkColorScheme(
    primary = AquaFluxColors.AmarilloClaro,
    secondary = AquaFluxColors.AmarilloOscuro,
    tertiary = AquaFluxColors.AzulMasClaro,
    background = AquaFluxColors.Negro,
    surface = AquaFluxColors.AzulOscuro,
    onPrimary = AquaFluxColors.Negro,
    onSecondary = AquaFluxColors.Negro,
    onBackground = AquaFluxColors.Blanco,
    onSurface = AquaFluxColors.Blanco
)

@Composable
fun AquaFluxTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AquaFluxColorScheme,
        content = content
    )
}
