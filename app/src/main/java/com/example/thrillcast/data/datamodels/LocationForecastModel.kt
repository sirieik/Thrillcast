data class LocationForecastObject(
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
    val units: Units,
    val updated_at: String
)

data class Timesery(
    val `data`: Data,
    val time: String
)

data class Units(
    val air_pressure_at_sea_level: String,
    val air_temperature: String,
    val cloud_area_fraction: String,
    val precipitation_amount: String,
    val relative_humidity: String,
    val wind_from_direction: String,
    val wind_speed: String
)

data class Data(
    val instant: Instant,
    val next_12_hours: Next12Hours,
    val next_1_hours: Next1Hours,
    val next_6_hours: Next6Hours
)

data class Instant(
    val details: Details
)

data class Next12Hours(
    val summary: Summary
)

data class Next1Hours(
    val details: DetailsXX,
    val summary: Summary
)

data class Next6Hours(
    val details: DetailsXX,
    val summary: Summary
)

data class Details(
    val air_pressure_at_sea_level: Double,
    val air_temperature: Double,
    val cloud_area_fraction: Double,
    val relative_humidity: Double,
    val wind_from_direction: Double,
    val wind_speed: Double
)

data class Summary(
    val symbol_code: String
)

data class DetailsXX(
    val precipitation_amount: Double
)