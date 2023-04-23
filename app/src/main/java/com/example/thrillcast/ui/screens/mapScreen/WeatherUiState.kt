import com.example.thrillcast.data.met.weatherforecast.WeatherForecast

open class WeatherUiState(
    val takeoff: Takeoff,
    val wind: Wind,

    val nowCastObject: Timeseries?,

    val windyWindsList: List<WindyWinds>?,

    val weatherForecast: List<WeatherForecast>,

)