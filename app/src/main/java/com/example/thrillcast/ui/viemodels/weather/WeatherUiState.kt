package com.example.thrillcast.ui.viemodels.weather

import Takeoff
import WeatherForecast
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.WindyWinds
import com.example.thrillcast.data.met.nowcast.Timesery

open class WeatherUiState(
    val takeoff: Takeoff,
    val wind: Wind,

    val nowCastObject: Timesery?,

    val windyWindsList: List<WindyWinds>?,

    val weatherForecast: List<WeatherForecast>,

    val locationForecast: List<WeatherForecast>?

)