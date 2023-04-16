import com.example.thrillcast.data.met.weatherforecast.WeatherForecast
import com.google.android.gms.maps.model.LatLng

open class HolfuyWeatherUiState(
    val takeoff: Takeoff,
    val wind: Wind,
    val weatherForecast: List<WeatherForecast>,
    val windSpeed: Double

)