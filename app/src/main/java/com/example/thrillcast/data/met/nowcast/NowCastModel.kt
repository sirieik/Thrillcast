package com.example.thrillcast.data.met.nowcast

data class NowCastObject(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)

data class Properties(
    val meta: Meta,
    val timeseries: List<Timesery>
)

data class Meta(
    val radar_coverage: String,
    val units: Units,
    val updated_at: String
)

data class Timesery(
    val `data`: Data,
    val time: String
)

data class Units(
    val air_temperature: String,
    val precipitation_amount: String,
    val precipitation_rate: String,
    val relative_humidity: String,
    val wind_from_direction: String,
    val wind_speed: String,
    val wind_speed_of_gust: String
)

data class Data(
    val instant: Instant,
    val next_1_hours: Next1Hours
)

data class Instant(
    val details: Details
)

data class Next1Hours(
    val details: DetailsX,
    val summary: Summary
)

data class Details(
    val air_temperature: Double,
    val precipitation_rate: Double,
    val relative_humidity: Double,
    val wind_from_direction: Double,
    val wind_speed: Double,
    val wind_speed_of_gust: Double
)

data class DetailsX(
    val precipitation_amount: Double
)

data class Summary(
    val symbol_code: String
)