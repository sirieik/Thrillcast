package com.example.thrillcast.ui.viemodels.weather

import com.example.thrillcast.ui.viemodels.map.Takeoff
import WeatherForecast
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.WindyWinds


open class CurrentWeatherUiState(
    val nowCastObject: WeatherForecast? = null,
    val wind: Wind?
)

open class ForecastUiState(
    val locationForecast: List<WeatherForecast>?
)

open class HeightWindUiState(
    val windyWindsList: List<WindyWinds>?
)

open class TakeoffUiState(
    val takeoff: Takeoff?
)

open class MultiCurrentWeatherUiState(
    val currentWeatherList: List<CurrentWeatherUiState>
)

open class LocationsWindUiState(
    val windList: List<Wind>
)
