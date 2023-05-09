package com.example.thrillcast.ui.viemodels.weather

import WeatherForecast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.HolfuyRepository
import com.example.thrillcast.data.repositories.MetRepository
import com.example.thrillcast.data.repositories.WindyRepository
import com.example.thrillcast.data.repositories.WindyWinds
import com.example.thrillcast.ui.viemodels.map.Takeoff
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val holfuyRepository: HolfuyRepository,
    val metRepository: MetRepository,
    val windyRepository: WindyRepository
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
            mutableListOf()
        )
    )

    val multiCurrentWeatherUiState: StateFlow<MultiCurrentWeatherUiState> = _multiCurrentWeatherUiState.asStateFlow()


    /*
    Generelt anbefalt å ikke returnere data dra viewModel utenom UIStates, men her så trengs
    dataen bare en gang og trenger ikke å oppdateres
     */
    fun retrieveWind(takeoff: Takeoff): Wind {
        var stationWind: Wind? = null
        viewModelScope.launch {
            stationWind = holfuyRepository.fetchHolfuyStationWeather(takeoff.id)
        }
        return stationWind ?: Wind(0, 0.0, 0.0, 0.0, "m/s")
    }
    fun retrieveCurrentWeather(takeoff: Takeoff) {
        viewModelScope.launch {
            val stationWind: Wind? = holfuyRepository.fetchHolfuyStationWeather(takeoff.id)
            val nowWeather: WeatherForecast? =
                try {
                    metRepository.fetchNowCastObject(takeoff.coordinates.latitude, takeoff.coordinates.longitude)?.properties?.timeseries?.get(0)
                } catch (e: Exception) {
                    null
                }
            _currentWeatherUiState.value = CurrentWeatherUiState(wind = stationWind, nowCastObject = nowWeather)
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

    fun addCurrentWeatherUiState(takeoff: Takeoff) {
        viewModelScope.launch {
            val stationWind: Wind? = holfuyRepository.fetchHolfuyStationWeather(takeoff.id)
            val nowWeather: WeatherForecast? =
                try {
                    metRepository.fetchNowCastObject(takeoff.coordinates.latitude, takeoff.coordinates.longitude)?.properties?.timeseries?.get(0)
                } catch (e: Exception) {
                    null
                }
            _multiCurrentWeatherUiState.value.currentWeatherList.add(
                Pair(
                    CurrentWeatherUiState(wind = stationWind, nowCastObject = nowWeather),
                    takeoff
                )
            )
        }
    }

    fun retrieveFavoritesWeather(favorites: List<Takeoff>) {
        viewModelScope.launch {
            favorites.forEach{

            }
        }
    }
}