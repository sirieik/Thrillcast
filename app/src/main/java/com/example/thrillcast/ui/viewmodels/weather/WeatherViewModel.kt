package com.example.thrillcast.ui.viewmodels.weather

import com.example.thrillcast.data.datamodels.WeatherForecast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.HolfuyRepository
import com.example.thrillcast.data.repositories.MetRepository
import com.example.thrillcast.data.repositories.WindyRepository
import com.example.thrillcast.data.repositories.WindyWinds
import com.example.thrillcast.ui.viewmodels.map.Takeoff
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val holfuyRepository: HolfuyRepository,
    private val metRepository: MetRepository,
    private val windyRepository: WindyRepository
    ) : ViewModel() {

    private val _currentWeatherUiState = MutableStateFlow(
        CurrentWeatherUiState(
            null,
            null
        )
    )

    val currentWeatherUiState: StateFlow<CurrentWeatherUiState> = _currentWeatherUiState.asStateFlow()

    private val _forecastUiState = MutableStateFlow(
        ForecastUiState(
            null
        )
    )

    val forecastWeatherUiState: StateFlow<ForecastUiState> = _forecastUiState.asStateFlow()

    private val _heightWindUiState = MutableStateFlow(
        HeightWindUiState(
            null
        )
    )

    val heightWindUiState: StateFlow<HeightWindUiState> = _heightWindUiState.asStateFlow()

    private val _takeoffUiState = MutableStateFlow(
        TakeoffUiState(
            null
        )
    )

    val takeoffUiState: StateFlow<TakeoffUiState> = _takeoffUiState.asStateFlow()

    private val _multiCurrentWeatherUiState = MutableStateFlow(
        MultiCurrentWeatherUiState(
            listOf()
        )
    )

    val multiCurrentWeatherUiState: StateFlow<MultiCurrentWeatherUiState> = _multiCurrentWeatherUiState.asStateFlow()


    private val _locationsWindUiState = MutableStateFlow(
        LocationsWindUiState(
            listOf()
        )
    )
    val locationsWindUiState: StateFlow<LocationsWindUiState> = _locationsWindUiState.asStateFlow()

    fun retrieveCurrentWeather(takeoff: Takeoff) {
        viewModelScope.launch {
            takeoff?.id?.let {
                val stationWind: Wind? = holfuyRepository.fetchHolfuyStationWeather(takeoff.id)
                val weather = metRepository.fetchNowCastObject(
                    takeoff.coordinates.latitude,
                    takeoff.coordinates.longitude
                )?.properties?.timeseries?.firstOrNull()

                _currentWeatherUiState.value = CurrentWeatherUiState(wind = stationWind, nowCastObject = weather)
            }
        }
    }

    fun retrieveForecastWeather(takeoff: Takeoff) {
        viewModelScope.launch {
            val locationForecast: List<WeatherForecast>? = metRepository.fetchLocationForecast(takeoff.coordinates.latitude, takeoff.coordinates.longitude)
            _forecastUiState.value = ForecastUiState(locationForecast = locationForecast)
        }
    }

    fun retrieveHeightWind(takeoff: Takeoff) {
        viewModelScope.launch {
            val windyWinds: List<WindyWinds> = windyRepository.fetchWindyWindsList(takeoff.coordinates.latitude, takeoff.coordinates.longitude)
            _heightWindUiState.value = HeightWindUiState(windyWindsList = windyWinds)
        }
    }

    fun updateChosenTakeoff(takeoff: Takeoff) {
        viewModelScope.launch {
            _takeoffUiState.value = TakeoffUiState(takeoff = takeoff)
        }
    }

    fun retrieveLocationsWind(locations: List<Takeoff>) {
        viewModelScope.launch {
            val locationWinds = mutableListOf<Wind>()
            locations.forEach{ location ->
                val wind = holfuyRepository.fetchHolfuyStationWeather(location.id)
                locationWinds.add(wind ?: Wind(0,0.0,0.0,0.0,"m/s"))
            }
            _locationsWindUiState.value = LocationsWindUiState(locationWinds)
        }
    }

    fun retrieveFavoritesWeather(favorites: List<Takeoff?>) {
        viewModelScope.launch {
            val currentWeatherList = mutableListOf<CurrentWeatherUiState>()

            favorites.forEach { favorite ->
                favorite?.id?.let {
                    val wind = holfuyRepository.fetchHolfuyStationWeather(favorite.id)
                    val weather = metRepository.fetchNowCastObject(
                        favorite.coordinates.latitude,
                        favorite.coordinates.longitude
                    )
                    currentWeatherList.add(
                        CurrentWeatherUiState(
                            weather?.properties?.timeseries?.firstOrNull(),
                            wind
                        )
                    )
                }
            }
            _multiCurrentWeatherUiState.value = MultiCurrentWeatherUiState(currentWeatherList)
        }
    }
}