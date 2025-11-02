package utt.equipo.hackathon.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.FlowPreview
import utt.equipo.hackathon.presentation.ui.theme.AquaFluxColors
import utt.equipo.hackathon.presentation.viewmodel.LoginState
@FlowPreview
@Composable
fun LoginScreen(
    uiState: LoginState,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    LaunchedEffect(uiState) {
        if (uiState is LoginState.Success) {
            onLoginSuccess()
        }
    }
    
    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(AquaFluxColors.AzulMasClaro),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Iniciar Sesion",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = AquaFluxColors.AmarilloClaro
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("User") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AquaFluxColors.AmarilloClaro,
                    unfocusedBorderColor = AquaFluxColors.AmarilloClaro,
                    focusedTextColor = AquaFluxColors.Blanco,
                    unfocusedTextColor = AquaFluxColors.Blanco
                ),
                shape = RoundedCornerShape(24.dp)
            )
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AquaFluxColors.AmarilloClaro,
                    unfocusedBorderColor = AquaFluxColors.AmarilloClaro,
                    focusedTextColor = AquaFluxColors.Blanco,
                    unfocusedTextColor = AquaFluxColors.Blanco
                ),
                shape = RoundedCornerShape(24.dp)
            )
            
            Button(
                onClick = { onLoginClick(username, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AquaFluxColors.AmarilloClaro
                ),
                shape = RoundedCornerShape(24.dp),
                enabled = uiState !is LoginState.Loading
            ) {
                if (uiState is LoginState.Loading) {
                    CircularProgressIndicator(color = AquaFluxColors.Negro, modifier = Modifier.size(20.dp))
                } else {
                    Text("Aceptar", color = AquaFluxColors.Negro, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            TextButton(onClick = onRegisterClick) {
                Text("Aún no tienes cuenta? Crea Una", color = AquaFluxColors.AmarilloClaro)
            }
            
            if (uiState is LoginState.Error) {
                Text(
                    text = uiState.message,
                    color = AquaFluxColors.Rojo,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
