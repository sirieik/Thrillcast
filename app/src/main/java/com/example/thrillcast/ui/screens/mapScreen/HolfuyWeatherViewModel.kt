import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.Repository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HolfuyWeatherViewModel : ViewModel() {

    val repo = Repository()

    private val _uiState = MutableStateFlow(
        HolfuyWeatherUiState(
            Takeoff(0, LatLng(0.0,0.0), "", 0, 0,0),
            Wind(0.0,0.0,0.0,"",0)
        )
    )

    val uiState: StateFlow<HolfuyWeatherUiState> = _uiState.asStateFlow()

    fun retrieveStationWeather(takeoff: Takeoff) {
        viewModelScope.launch {
            val weather: Wind? = repo.fetchHolfuyStationWeather(takeoff.id)

            _uiState.value = weather?.let { HolfuyWeatherUiState(takeoff = takeoff, wind = it) }!!
        }
    }
}