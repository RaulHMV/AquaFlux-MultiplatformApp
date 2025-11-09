package utt.equipo.hackathon.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import utt.equipo.hackathon.presentation.ui.components.LineChart
import utt.equipo.hackathon.presentation.ui.theme.AquaFluxColors
import utt.equipo.hackathon.presentation.ui.theme.MontserratFontFamily
import utt.equipo.hackathon.presentation.viewmodel.ChartState
import utt.equipo.hackathon.presentation.viewmodel.DashboardState

@Composable
fun DashboardScreen(
    uiState: DashboardState,
    chartState: ChartState,
    userFirstName: String? = null,
    onMenuClick: () -> Unit,
    onRefresh: () -> Unit
) {
    val MontserratFamily = MontserratFontFamily()
    
    LaunchedEffect(Unit) {
        onRefresh()
        while (true) {
            delay(30000)
            onRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AquaFluxColors.AzulMasClaro) // Fondo azul oscuro general
    ) {
        when (uiState) {
            is DashboardState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AquaFluxColors.AmarilloClaro)
                }
            }
            is DashboardState.Success -> {
                // CAMBIO: Columna principal con scroll y padding para la "Safe Zone"
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        // Padding para la "Safe Zone" (arriba, abajo, y lados)
                        .padding(horizontal = 24.dp, vertical = 24.dp), 
                    // Espacio automático entre todos los elementos
                    verticalArrangement = Arrangement.spacedBy(24.dp) 
                ) {
                    
                    // 1. Header Card (Con todos los ajustes)
                    Surface(
                        modifier = Modifier.fillMaxWidth(), // Ahora respeta el padding del padre
                        color = AquaFluxColors.AzulOscuro,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 24.dp), // Padding interno
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // CAMBIO: Column para "Hola," y "Mandujano"
                            Column {
                                Text(
                                    text = "Hola,",
                                    fontSize = 30.sp, // CAMBIO: Tamaño
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = MontserratFamily,
                                    color = AquaFluxColors.AmarilloClaro,
                                    letterSpacing = 1.5.sp // CAMBIO: Espaciado
                                )
                                Text(
                                    text = uiState.data.user.first_name,
                                    fontSize = 30.sp, // CAMBIO: Tamaño
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = MontserratFamily,
                                    color = AquaFluxColors.AmarilloClaro,
                                    letterSpacing = 1.5.sp // CAMBIO: Espaciado
                                )
                            }
                            IconButton(onClick = onMenuClick) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = AquaFluxColors.AmarilloClaro,
                                    // CAMBIO: Icono más grande
                                    modifier = Modifier.size(40.dp) 
                                )
                            }
                        }
                    }

                    // 2. "Estado" Block
                    // Lógica de detección de fugas:
                    // - Presostato = 0, YSF = 0 o mayor → No se detectaron fugas
                    // - Presostato = 1, YSF = 0 → No hay fuga
                    // - Presostato = 1, YSF > 0 → Se detectaron fugas
                    val presostato = if (uiState.data.sensors.leakDetector.hasLeak) 1 else 0
                    val ysf = uiState.data.sensors.waterFlow.liters
                    
                    val hasFugaDetectada = when {
                        presostato == 0 -> false // Presostato en 0 → No hay fuga
                        presostato == 1 && ysf == 0.0 -> false // Presostato en 1 pero YSF en 0 → No hay fuga
                        presostato == 1 && ysf > 0.0 -> true // Presostato en 1 y YSF > 0 → Sí hay fuga
                        else -> false
                    }
                    
                    Text(
                        text = "Estado",
                        fontSize = 30.sp, // CAMBIO: Tamaño
                        fontWeight = FontWeight.Bold,
                        fontFamily = MontserratFamily,
                        color = AquaFluxColors.AmarilloClaro,
                        letterSpacing = 1.5.sp // CAMBIO: Espaciado
                    )
                    Text(
                        text = if (hasFugaDetectada) "Se detectaron fugas" else "No se detectaron fugas",
                        fontSize = 20.sp, // CAMBIO: Tamaño
                        fontWeight = FontWeight.Normal,
                        fontFamily = MontserratFamily,
                        color = AquaFluxColors.AmarilloClaro,
                        letterSpacing = 1.5.sp // CAMBIO: Espaciado
                    )

                    // 3. "Litros" Card (CAMBIO: Título DENTRO de la card)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = AquaFluxColors.AzulOscuro,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.Start, // Título a la izquierda
                            verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre título y número
                        ) {
                            Text(
                                text = "Litros detectados en fuga",
                                fontSize = 20.sp, // CAMBIO: Tamaño
                                fontWeight = FontWeight.Medium,
                                fontFamily = MontserratFamily,
                                color = AquaFluxColors.AmarilloClaro,
                                letterSpacing = 1.5.sp // CAMBIO: Espaciado
                            )
                            Text(
                                text = uiState.data.sensors.waterFlow.liters.toString(),
                                fontSize = 52.sp, // CAMBIO: Tamaño mucho más grande
                                fontWeight = FontWeight.Medium,
                                fontFamily = MontserratFamily,
                                color = AquaFluxColors.AmarilloClaro,
                                letterSpacing = 1.5.sp, // CAMBIO: Espaciado
                                // Centra el número en la tarjeta
                                modifier = Modifier.align(Alignment.CenterHorizontally) 
                            )
                        }
                    }

                    // 4. "Gráfica" Card (Sin cambios, se mantiene perfecta)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = AquaFluxColors.AzulOscuro,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            when (chartState) {
                                is ChartState.Loading -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(200.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = AquaFluxColors.AmarilloClaro)
                                    }
                                }
                                is ChartState.Success -> {
                                    LineChart(
                                        data = chartState.data,
                                        lineColor = AquaFluxColors.AmarilloClaro,
                                        backgroundColor = AquaFluxColors.AzulOscuro
                                    )
                                }
                                is ChartState.Error -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(200.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Error al cargar gráfico",
                                            color = AquaFluxColors.Rojo.copy(alpha = 0.7f),
                                            fontSize = 16.sp, // CAMBIO: Tamaño
                                            fontFamily = MontserratFamily,
                                            letterSpacing = 1.5.sp // CAMBIO: Espaciado
                                        )
                                    }
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }
            is DashboardState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        uiState.message,
                        color = AquaFluxColors.Rojo,
                        fontFamily = MontserratFamily,
                        letterSpacing = 1.5.sp // CAMBIO: Espaciado
                    )
                }
            }
            else -> {}
        }
    }
}
