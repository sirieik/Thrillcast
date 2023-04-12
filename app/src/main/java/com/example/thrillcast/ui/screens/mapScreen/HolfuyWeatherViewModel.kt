import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HolfuyWeatherViewModel : ViewModel() {

    val repo = Repository()

    private val _uiState = MutableStateFlow(HolfuyWeatherUiState(0, Wind(0.0,0.0,0.0,"",0)))

    val uiState: StateFlow<HolfuyWeatherUiState> = _uiState.asStateFlow()

    private val HolfuyClient = HolfuyModel()

    fun retrieveStationWeather(id: Int) {
        viewModelScope.launch {
            val weather: Wind? = repo.fetchHolfuyStationWeather(id)

            _uiState.value = weather?.let { HolfuyWeatherUiState(locationId = id, wind = it) }!!
        }
    }
}