package com.example.thrillcast.data.datamodels

data class HolfuyObject(
    val dateTime: String,
    val light: Int,
    val stationId: Int,
    val stationName: String,
    val temperature: Double,
    val wind: Wind
)

data class Wind(
    val direction: Int,
    val gust: Double,
    val min: Double,
    val speed: Double,
    val unit: String
)