package com.example.thrillcast.data.met.weatherforecast

import java.time.ZonedDateTime

data class WeatherForecast (
    var time : ZonedDateTime,
    var data : Data?
)


