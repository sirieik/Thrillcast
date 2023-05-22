package com.example.thrillcast.data.datamodels

import java.time.ZonedDateTime

//Vi bruker samme datamodell for både LocationForecast og NowCast, og henter kun den dataen vi trenger.

/**
 * Representerer et com.example.thrillcast.data.datamodels.MetObject som inneholder egenskaper relatert til værvarsel.
 *
 * @property properties Egenskapene til com.example.thrillcast.data.datamodels.MetObject.
 */
data class MetObject(
    val properties: Properties?
)

/**
 * Representerer egenskaper som inneholder værvarsel-timeseries.
 *
 * @property timeseries Listen over værvarsel-timeseries.
 */
data class Properties(
    val timeseries: List<WeatherForecast>?
)

/**
 * Representerer et værvarsel for en spesifikk tid.
 *
 * @property data com.example.thrillcast.data.datamodels.Data knyttet til værvarselet.
 * @property time Dato og tid for værvarselet.
 */
data class WeatherForecast(
    val data: Data?,
    val time: ZonedDateTime?
)

/**
 * Representerer data knyttet til et værvarsel.
 *
 * @property instant com.example.thrillcast.data.datamodels.Data for øyeblikket i værvarselet.
 * @property next_1_hours Værvarsel for de neste 1 timene.
 * @property next_6_hours Værvarsel for de neste 6 timene.
 */
data class Data(
    val instant: Instant,
    val next_1_hours: NextHours?,
    val next_6_hours: NextHours?
)

/**
 * Representerer data for øyeblikket i et værvarsel.
 *
 * @property details Detaljer om øyeblikksdata for været.
 */
data class Instant(
    val details: Details?
)

/**
 * Representerer værvarsel for de kommende timene.
 *
 * @property details Detaljer om værvarsel for de kommende timene.
 * @property summary Oppsummering av værvarsel for de kommende timene.
 */
data class NextHours(
    val details: DetailsX?,
    val summary: Summary?
)

/**
 * Representerer detaljer om øyeblikksdata for været.
 *
 * @property air_temperature Lufttemperaturen.
 * @property wind_from_direction Vindretningen.
 * @property wind_speed Vindhastigheten.
 * @property wind_speed_of_gust Vindhastigheten for vindkast.
 */
data class Details(
    val air_temperature: Double?,
    val wind_from_direction: Double?,
    val wind_speed: Double?,
    val wind_speed_of_gust: Double?
)

/**
 * Representerer detaljer om værdata for de neste timene.
 *
 * @property precipitation_amount Mengden nedbør.
 */
data class DetailsX(
    val precipitation_amount: Double?
)

/**
 * Representerer oppsummeringen av værdata for de neste timene.
 *
 * @property symbol_code Symbolkoden som representerer værforholdene.
 */
data class Summary(
    val symbol_code: String?
)