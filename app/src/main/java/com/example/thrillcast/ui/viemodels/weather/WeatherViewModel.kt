package com.example.thrillcast.ui.viemodels.weather

import HolfuyRepository
import MetRepository
import WeatherForecast
import WindyRepository
import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.HolfuyRepository
import com.example.thrillcast.data.repositories.MetRepository
import com.example.thrillcast.data.repositories.WindyRepository
import com.example.thrillcast.data.repositories.WindyWinds
import com.example.thrillcast.ui.viemodels.map.Takeoff
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel() : ViewModel() {

    private val metRepo = MetRepository()
    private val holfuyRepo = HolfuyRepository()
    private val windyRepo = WindyRepository()

/*

    private val _uiState = MutableStateFlow(
        WeatherUiState(
            Takeoff(0, LatLng(0.0, 0.0), "", 0, 0, 0),
            Wind(0, 0.0, 0.0, 0.0, ""),
            null,
            emptyList(),
            null
        )
    )

    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

 */

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

/*
    fun retrieveStationWeather(takeoff: Takeoff) {
        viewModelScope.launch {

            val weather: Wind? = holfuyRepo.fetchHolfuyStationWeather(takeoff.id)

            val windyWindsList: List<WindyWinds> = windyRepo.fetchWindyWindsList(takeoff.coordinates.latitude, takeoff.coordinates.longitude)

            val nowCastObject: WeatherForecast? =
                metRepo.fetchNowCastObject(takeoff.coordinates.latitude, takeoff.coordinates.longitude).properties?.timeseries?.get(0)

            val locationForecast: List<WeatherForecast>? = metRepo.fetchLocationForecast(takeoff.coordinates.latitude, takeoff.coordinates.longitude)

            _uiState.value = weather?.let {
                WeatherUiState(
                    takeoff = takeoff, wind = it, nowCastObject = nowCastObject,
                    windyWindsList = windyWindsList,
                    locationForecast = locationForecast
                )
            }!!
        }
    }

 */

    fun retrieveCurrentWeather(takeoff: Takeoff) {
        viewModelScope.launch {
            val stationWind: Wind? = holfuyRepo.fetchHolfuyStationWeather(takeoff.id)
            val nowWeather: WeatherForecast? =
                try {
                    metRepo.fetchNowCastObject(takeoff.coordinates.latitude, takeoff.coordinates.longitude)?.properties?.timeseries?.get(0)
                } catch (e: Exception) {
                    null
                }
            _currentWeatherUiState.value = CurrentWeatherUiState(wind = stationWind, nowCastObject = nowWeather)
        }
    }

    fun retrieveForecastWeather(takeoff: Takeoff) {
        viewModelScope.launch {
            val locationForecast: List<WeatherForecast>? = metRepo.fetchLocationForecast(takeoff.coordinates.latitude, takeoff.coordinates.longitude)
            _forecastUiState.value = ForecastUiState(locationForecast = locationForecast)
        }
    }

    fun retrieveHeightWind(takeoff: Takeoff) {
        viewModelScope.launch {
            val windyWinds: List<WindyWinds> = windyRepo.fetchWindyWindsList(takeoff.coordinates.latitude, takeoff.coordinates.longitude)
            _heightWindUiState.value = HeightWindUiState(windyWindsList = windyWinds)
        }
    }

    fun updateChosenTakeoff(takeoff: Takeoff) {
        viewModelScope.launch {
            _takeoffUiState.value = TakeoffUiState(takeoff = takeoff)
        }
    }
}