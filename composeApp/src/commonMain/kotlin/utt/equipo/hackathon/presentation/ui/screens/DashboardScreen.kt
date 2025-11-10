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
import utt.equipo.hackathon.presentation.viewmodel.TimeFilter

@Composable
fun DashboardScreen(
    uiState: DashboardState,
    chartState: ChartState,
    selectedTimeFilter: TimeFilter,
    showPermissionDialog: Boolean,
    userFirstName: String? = null,
    onMenuClick: () -> Unit,
    onRefresh: () -> Unit,
    onTimeFilterChange: (TimeFilter) -> Unit,
    onPermissionDialogConfirm: () -> Unit,
    onPermissionDialogDismiss: () -> Unit
) {
    val montserratFamily = MontserratFontFamily()
    
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = onPermissionDialogDismiss,
            title = {
                Text(
                    "Activar Notificaciones",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "AquaFlux necesita permisos de notificaciones para alertarte cuando se detecten fugas de agua.",
                    fontFamily = montserratFamily
                )
            },
            confirmButton = {
                Button(
                    onClick = onPermissionDialogConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AquaFluxColors.AmarilloClaro
                    )
                ) {
                    Text("Activar", fontFamily = montserratFamily, color = AquaFluxColors.AzulOscuro)
                }
            },
            dismissButton = {
                TextButton(onClick = onPermissionDialogDismiss) {
                    Text("Ahora no", fontFamily = montserratFamily, color = AquaFluxColors.AmarilloClaro)
                }
            }
        )
    }
    
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
            .background(AquaFluxColors.AzulMasClaro)
    ) {
        when (uiState) {
            is DashboardState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AquaFluxColors.AmarilloClaro)
                }
            }
            is DashboardState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 24.dp), 
                    verticalArrangement = Arrangement.spacedBy(24.dp) 
                ) {
                    
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = AquaFluxColors.AzulOscuro,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Hola,",
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = montserratFamily,
                                    color = AquaFluxColors.AmarilloClaro,
                                    letterSpacing = 1.5.sp
                                )
                                Text(
                                    text = uiState.data.user.first_name,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = montserratFamily,
                                    color = AquaFluxColors.AmarilloClaro,
                                    letterSpacing = 1.5.sp
                                )
                            }
                            IconButton(onClick = onMenuClick) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = AquaFluxColors.AmarilloClaro,
                                    modifier = Modifier.size(40.dp) 
                                )
                            }
                        }
                    }

                    val presostato = if (uiState.data.sensors.leakDetector.hasLeak) 1 else 0
                    val ysf = uiState.data.sensors.waterFlow.liters
                    
                    val hasFugaDetectada = when {
                        presostato == 0 -> false
                        presostato == 1 && ysf == 0.0 -> false
                        presostato == 1 && ysf > 0.0 -> true
                        else -> false
                    }
                    
                    Text(
                        text = "Estado",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFamily,
                        color = AquaFluxColors.AmarilloClaro,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = if (hasFugaDetectada) "Se detectaron fugas" else "No se detectaron fugas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = montserratFamily,
                        color = AquaFluxColors.AmarilloClaro,
                        letterSpacing = 1.5.sp
                    )

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = AquaFluxColors.AzulOscuro,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Litros detectados",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = montserratFamily,
                                color = AquaFluxColors.AmarilloClaro,
                                letterSpacing = 1.5.sp
                            )
                            Text(
                                text = uiState.data.sensors.waterFlow.liters.toString(),
                                fontSize = 52.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = montserratFamily,
                                color = AquaFluxColors.AmarilloClaro,
                                letterSpacing = 1.5.sp
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = AquaFluxColors.AzulOscuro,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                TimeFilter.entries.forEach { filter ->
                                    Button(
                                        onClick = { onTimeFilterChange(filter) },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (selectedTimeFilter == filter) {
                                                AquaFluxColors.AmarilloClaro
                                            } else {
                                                AquaFluxColors.AzulMasClaro
                                            },
                                            contentColor = if (selectedTimeFilter == filter) {
                                                AquaFluxColors.AzulOscuro
                                            } else {
                                                AquaFluxColors.AmarilloClaro
                                            }
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = filter.label,
                                            fontFamily = montserratFamily,
                                            fontWeight = if (selectedTimeFilter == filter) {
                                                FontWeight.Bold
                                            } else {
                                                FontWeight.Normal
                                            },
                                            fontSize = 14.sp,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                }
                            }
                            
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
                                            fontSize = 16.sp,
                                            fontFamily = montserratFamily,
                                            letterSpacing = 1.5.sp
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
                        fontFamily = montserratFamily,
                        letterSpacing = 1.5.sp // CAMBIO: Espaciado
                    )
                }
            }
            else -> {}
        }
    }
}
