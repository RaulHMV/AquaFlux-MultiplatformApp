package utt.equipo.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utt.equipo.hackathon.domain.usecases.RegisterUseCase
import utt.equipo.hackathon.util.Result

/**
 * Estados del Registro
 */
sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

/**
 * ViewModel para la pantalla de Registro
 */
class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()
    
    fun register(firstName: String, apellido: String, username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = RegisterState.Loading
            
            // Concatenar firstName + apellido como requiere el backend
            val fullName = "$firstName $apellido".trim()
            
            when (val result = registerUseCase(fullName, username, password)) {
                is Result.Success -> {
                    _uiState.value = RegisterState.Success("Registro exitoso!")
                }
                is Result.Error -> {
                    _uiState.value = RegisterState.Error(result.message)
                }
                is Result.Loading -> {
                    // Already in Loading state
                }
            }
        }
    }
    
    fun resetState() {
        _uiState.value = RegisterState.Idle
    }
}
