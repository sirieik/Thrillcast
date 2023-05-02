import java.time.ZonedDateTime

//Vi bruker samme datamodell for både LocationForecast og NowCast, og henter kun den dataen vi trenger.
data class MetObject(
    val properties: Properties,
)

data class Properties(
    // val meta: Meta,
    val timeseries: List<WeatherForecast>
)

data class WeatherForecast(
    val data: Data,
    val time: ZonedDateTime
)

data class Data(
    val instant: Instant,
    val next_1_hours: NextHours?,
    val next_6_hours: NextHours?
)

data class Instant(
    val details: Details
)

data class NextHours(
    val details: DetailsX,
    val summary: Summary?
)

data class Details(
    val air_temperature: Double,
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