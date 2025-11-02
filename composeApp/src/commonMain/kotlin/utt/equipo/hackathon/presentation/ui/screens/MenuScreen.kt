package utt.equipo.hackathon.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import utt.equipo.hackathon.di.DependencyContainer
import utt.equipo.hackathon.presentation.ui.theme.AquaFluxColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var newName by remember { mutableStateOf("") }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    
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
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = AquaFluxColors.AmarilloClaro)
                    }
                    Text("MENU", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = AquaFluxColors.AmarilloClaro)
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Cambiar nombre
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = AquaFluxColors.AzulOscuro,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Cambiar nombre", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AquaFluxColors.AmarilloOscuro)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text("Nombre nuevo") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AquaFluxColors.AmarilloClaro,
                                unfocusedBorderColor = AquaFluxColors.AmarilloClaro
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* TODO */ },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AquaFluxColors.AmarilloClaro),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text("Aceptar", color = AquaFluxColors.Negro, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                // Cambiar contraseña
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = AquaFluxColors.AzulOscuro,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Cambiar contraseña", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AquaFluxColors.AmarilloOscuro)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = oldPassword,
                            onValueChange = { oldPassword = it },
                            label = { Text("Contraseña anterior") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AquaFluxColors.AmarilloClaro,
                                unfocusedBorderColor = AquaFluxColors.AmarilloClaro
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("Contraseña nueva") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AquaFluxColors.AmarilloClaro,
                                unfocusedBorderColor = AquaFluxColors.AmarilloClaro
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* TODO */ },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AquaFluxColors.AmarilloClaro),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text("Aceptar", color = AquaFluxColors.Negro, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                // Cerrar Sesión
                Button(
                    onClick = {
                        scope.launch {
                            DependencyContainer.authRepository.logout()
                            onLogout()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AquaFluxColors.Rojo),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Cerrar Sesión", color = AquaFluxColors.Blanco, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
