package utt.equipo.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utt.equipo.hackathon.domain.usecases.LoginUseCase
import utt.equipo.hackathon.util.Result

/**
 * Estados del Login
 */
sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val message: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

/**
 * ViewModel para la pantalla de Login
 */
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Idle)
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()
    
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginState.Loading
            
            when (val result = loginUseCase(username, password)) {
                is Result.Success -> {
                    _uiState.value = LoginState.Success("Login exitoso!")
                }
                is Result.Error -> {
                    _uiState.value = LoginState.Error(result.message)
                }
                is Result.Loading -> {
                    // Already in Loading state
                }
            }
        }
    }
    
    fun resetState() {
        _uiState.value = LoginState.Idle
    }
}
