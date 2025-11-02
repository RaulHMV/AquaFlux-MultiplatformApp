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
    val displayName = userFirstName?.trim().takeUnless { it.isNullOrEmpty() } ?: "Usuario"

    LaunchedEffect(Unit) {
        while (true) {
            delay(30000)
            onRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AquaFluxColors.AzulMasClaro)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = AquaFluxColors.AzulOscuro,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (uiState) {
                            is DashboardState.Success -> "Hola, ${uiState.data.user.first_name}"
                            else -> "Hola, Usuario"
                        },
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AquaFluxColors.AmarilloClaro
                    )
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = AquaFluxColors.AmarilloClaro)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (uiState) {
                is DashboardState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AquaFluxColors.AmarilloClaro)
                    }
                }
                is DashboardState.Success -> {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Estado Card
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = AquaFluxColors.AzulOscuro,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text("Estado", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AquaFluxColors.AmarilloOscuro)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (uiState.data.sensors.leakDetector.hasLeak) "Fuga detectada" else "Sin fugas",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (uiState.data.sensors.leakDetector.hasLeak) AquaFluxColors.Rojo else AquaFluxColors.Verde
                                )
                            }
                        }

                        // Litros Card
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = AquaFluxColors.AzulOscuro,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text("Litros detectados en fuga", fontSize = 16.sp, color = AquaFluxColors.Blanco)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "${uiState.data.sensors.waterFlow.liters} L",
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AquaFluxColors.AmarilloClaro
                                )
                            }
                        }

                        // Chart Card
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = AquaFluxColors.AzulOscuro,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    "Historial de Fuga",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AquaFluxColors.AmarilloOscuro
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                
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
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                    else -> {}
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                is DashboardState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(uiState.message, color = AquaFluxColors.Rojo)
                    }
                }
                else -> {}
            }
        }
    }
}
