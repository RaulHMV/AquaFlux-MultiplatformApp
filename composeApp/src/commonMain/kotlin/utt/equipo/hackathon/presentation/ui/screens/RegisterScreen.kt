package utt.equipo.hackathon.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utt.equipo.hackathon.presentation.ui.theme.AquaFluxColors
import utt.equipo.hackathon.presentation.viewmodel.RegisterState

@Composable
fun RegisterScreen(
    uiState: RegisterState,
    onRegisterClick: (String, String, String, String) -> Unit,
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    LaunchedEffect(uiState) {
        if (uiState is RegisterState.Success) {
            onRegisterSuccess()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AquaFluxColors.AzulMasClaro),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("REGISTRO", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = AquaFluxColors.AmarilloClaro)
            
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Primer Nombre") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AquaFluxColors.AmarilloClaro,
                    unfocusedBorderColor = AquaFluxColors.AmarilloClaro
                ),
                shape = RoundedCornerShape(24.dp)
            )
            
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AquaFluxColors.AmarilloClaro,
                    unfocusedBorderColor = AquaFluxColors.AmarilloClaro
                ),
                shape = RoundedCornerShape(24.dp)
            )
            
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("User") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AquaFluxColors.AmarilloClaro,
                    unfocusedBorderColor = AquaFluxColors.AmarilloClaro
                ),
                shape = RoundedCornerShape(24.dp)
            )
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AquaFluxColors.AmarilloClaro,
                    unfocusedBorderColor = AquaFluxColors.AmarilloClaro
                ),
                shape = RoundedCornerShape(24.dp)
            )
            
            Button(
                onClick = { onRegisterClick(firstName, apellido, username, password) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AquaFluxColors.AmarilloClaro),
                shape = RoundedCornerShape(24.dp),
                enabled = uiState !is RegisterState.Loading
            ) {
                if (uiState is RegisterState.Loading) {
                    CircularProgressIndicator(color = AquaFluxColors.Negro, modifier = Modifier.size(20.dp))
                } else {
                    Text("Aceptar", color = AquaFluxColors.Negro, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            if (uiState is RegisterState.Error) {
                Text(
                    text = uiState.message,
                    color = AquaFluxColors.Rojo
                )
            }
            
            TextButton(onClick = onBackClick) {
                Text("Volver", color = AquaFluxColors.AmarilloClaro)
            }
        }
    }
}
