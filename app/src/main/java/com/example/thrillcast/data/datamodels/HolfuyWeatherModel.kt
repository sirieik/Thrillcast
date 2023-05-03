package com.example.thrillcast.data.datamodels

//Dette er dataklassene vi trenger for å parse responsen fra holfuyAPIet med værdata for
//stasjonsIDen vi sender med i kallet
data class HolfuyObject(
    val dateTime: String?,
    val light: Int?,
    val stationId: Int?,
    val stationName: String?,
    val temperature: Double?,
    val wind: Wind?
)

data class Wind(
    val direction: Int?,
    val gust: Double?,
    val min: Double?,
    val speed: Double?,
    val unit: String?
)