package utt.equipo.hackathon.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import hackathon.composeapp.generated.resources.Res
import hackathon.composeapp.generated.resources.montserrat_regular
import hackathon.composeapp.generated.resources.montserrat_medium
import hackathon.composeapp.generated.resources.montserrat_bold

/**
 * Familia de fuentes Montserrat para la aplicación
 */
@Composable
fun MontserratFontFamily(): FontFamily {
    return FontFamily(
        Font(
            resource = Res.font.montserrat_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.montserrat_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.montserrat_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        )
    )
}
