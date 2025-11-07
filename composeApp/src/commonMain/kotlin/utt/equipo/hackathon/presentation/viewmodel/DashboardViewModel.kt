package utt.equipo.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utt.equipo.hackathon.data.models.ChartDataPoint
import utt.equipo.hackathon.data.models.DashboardData
import utt.equipo.hackathon.domain.usecases.GetChartDataUseCase
import utt.equipo.hackathon.domain.usecases.GetDashboardUseCase
import utt.equipo.hackathon.util.Result

/**
 * Estados del Dashboard
 */
sealed class DashboardState {
    data object Idle : DashboardState()
    data object Loading : DashboardState()
    data class Success(val data: DashboardData) : DashboardState()
    data class Error(val message: String) : DashboardState()
}

/**
 * Estado de la gráfica
 */
sealed class ChartState {
    data object Idle : ChartState()
    data object Loading : ChartState()
    data class Success(val data: List<ChartDataPoint>) : ChartState()
    data class Error(val message: String) : ChartState()
}

/**
 * ViewModel para la pantalla de Dashboard
 */
class DashboardViewModel(
    private val getDashboardUseCase: GetDashboardUseCase,
    private val getChartDataUseCase: GetChartDataUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<DashboardState>(DashboardState.Idle)
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()
    
    private val _chartState = MutableStateFlow<ChartState>(ChartState.Idle)
    val chartState: StateFlow<ChartState> = _chartState.asStateFlow()
    
    init {
        loadDashboard()
        loadChartData()
    }
    
    /**
     * Refrescar todos los datos del dashboard
     */
    fun refresh() {
        loadDashboard()
        loadChartData()
    }
    
    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = DashboardState.Loading
            
            when (val result = getDashboardUseCase()) {
                is Result.Success -> {
                    _uiState.value = DashboardState.Success(result.data)
                }
                is Result.Error -> {
                    _uiState.value = DashboardState.Error(result.message)
                }
                is Result.Loading -> {
                    // Already in Loading state
                }
            }
        }
    }
    
    fun loadChartData() {
        viewModelScope.launch {
            _chartState.value = ChartState.Loading
            
            when (val result = getChartDataUseCase()) {
                is Result.Success -> {
                    // Invertir el orden: datos más antiguos a la izquierda, más nuevos a la derecha
                    val reversedData = result.data.reversed()
                    _chartState.value = ChartState.Success(reversedData)
                }
                is Result.Error -> {
                    _chartState.value = ChartState.Error(result.message)
                }
                is Result.Loading -> {
                    // Already in Loading state
                }
            }
        }
    }
}
