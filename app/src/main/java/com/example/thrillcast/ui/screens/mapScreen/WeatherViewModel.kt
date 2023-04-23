import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.Repository
import com.example.thrillcast.data.met.weatherforecast.WeatherForecast
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    val repo = Repository()

    private val _uiState = MutableStateFlow(
        WeatherUiState(
            Takeoff(0, LatLng(0.0,0.0), "", 0, 0,0),
            Wind(0.0,0.0,0.0,"",0),
            null,
            emptyList(),
            emptyList(),
        )
    )

    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun retrieveStationWeather(takeoff: Takeoff) {
        viewModelScope.launch {

            val weather: Wind? = repo.fetchHolfuyStationWeather(takeoff.id)
            val weatherForecast: List<WeatherForecast> = repo.fetchMetWeatherForecast(takeoff.coordinates.latitude, takeoff.coordinates.longitude)

            val windyWindsList: List<WindyWinds> = repo.fetchWindyWindsList("$takeoff.coordinates.latitude", "$takeoff.coordinates.longitude")

            val nowCastObject: Timeseries? = repo.fetchNowCastObject(takeoff.coordinates.latitude, takeoff.coordinates.longitude).properties?.timeseries?.get(0)

            Log.d("Activity", "${nowCastObject==null}")

            if (nowCastObject != null) {
                Log.d("Activity", "${nowCastObject.data?.instant?.details?.airTemperature}")
            }

            _uiState.value = weather?.let { WeatherUiState(takeoff = takeoff, wind = it, nowCastObject = nowCastObject, windyWindsList = windyWindsList, weatherForecast = weatherForecast) }!!

        }
    }
}