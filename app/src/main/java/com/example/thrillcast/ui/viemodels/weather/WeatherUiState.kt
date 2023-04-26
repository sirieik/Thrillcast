package com.example.thrillcast.ui.viemodels.weather

import com.example.thrillcast.ui.viemodels.map.Takeoff
import WeatherForecast
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.WindyWinds

open class WeatherUiState(
    val takeoff: Takeoff,
    val wind: Wind,

    val nowCastObject: WeatherForecast?,

    val windyWindsList: List<WindyWinds>?,

    val weatherForecast: List<WeatherForecast>,

    val locationForecast: List<WeatherForecast>?

)