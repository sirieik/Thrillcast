package com.example.thrillcast.ui.viemodels.weather

import com.example.thrillcast.ui.viemodels.map.Takeoff
import WeatherForecast
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.WindyWinds

/*
open class WeatherUiState(

    val takeoff: Takeoff,

    val wind: Wind,

    val nowCastObject: WeatherForecast?,

    val windyWindsList: List<WindyWinds>?,

    val locationForecast: List<WeatherForecast>?

)

 */

open class CurrentWeatherUiState(

    val nowCastObject: WeatherForecast?,
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
