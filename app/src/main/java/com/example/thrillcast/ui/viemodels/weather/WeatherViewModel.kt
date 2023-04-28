package com.example.thrillcast.ui.viemodels.weather

import HolfuyRepository
import MetRepository
import com.example.thrillcast.ui.viemodels.map.Takeoff
import WeatherForecast
import WindyRepository
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.WindyWinds
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel() : ViewModel() {

    val metRepo = MetRepository()
    val holfuyRepo = HolfuyRepository()
    val windyRepo = WindyRepository()


    private val _uiState = MutableStateFlow(
        WeatherUiState(
            Takeoff(0, LatLng(0.0, 0.0), "", 0, 0, 0),
            Wind(0, 0.0, 0.0, 0.0, ""),
            null,
            emptyList(),
            emptyList(),
            null
        )
    )

    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun retrieveStationWeather(takeoff: Takeoff) {
        viewModelScope.launch {

            val weather: Wind? = holfuyRepo.fetchHolfuyStationWeather(takeoff.id)
            val weatherForecast: List<WeatherForecast> = metRepo.fetchMetWeatherForecast(takeoff.coordinates.latitude, takeoff.coordinates.longitude)

            val windyWindsList: List<WindyWinds> = windyRepo.fetchWindyWindsList(takeoff.coordinates.latitude, takeoff.coordinates.longitude)

            val nowCastObject: WeatherForecast? = metRepo.fetchNowCastObject(takeoff.coordinates.latitude, takeoff.coordinates.longitude).properties?.timeseries?.get(0)

            val locationForecast: List<WeatherForecast> = metRepo.fetchLocationForecast(takeoff.coordinates.latitude, takeoff.coordinates.longitude)

            Log.d("Activity", "${nowCastObject==null}")

            if (nowCastObject != null) {
                Log.d("Activity", "${nowCastObject.data?.instant?.details?.air_temperature}")
            }

            _uiState.value = weather?.let {
                WeatherUiState(
                    takeoff = takeoff, wind = it, nowCastObject = nowCastObject,
                    windyWindsList = windyWindsList, weatherForecast = weatherForecast,
                    locationForecast = locationForecast
                )
            }!!
        }
    }
}