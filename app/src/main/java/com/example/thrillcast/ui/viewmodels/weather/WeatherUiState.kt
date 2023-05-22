package com.example.thrillcast.ui.viewmodels.weather

import com.example.thrillcast.ui.common.Takeoff
import com.example.thrillcast.data.datamodels.WeatherForecast
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.WindyWinds


/**
 * Klasse for å representere brukergrensesnittstatusen for nåværende vær.
 *
 * @property nowCastObject Værmeldingsobjektet for nåværende vær. Kan være null.
 * @property wind Vindobjekt som inneholder vinddata. Kan være null.
 */
open class CurrentWeatherUiState(
    val nowCastObject: WeatherForecast? = null,
    val wind: Wind?
)

/**
 * Klasse for å representere brukergrensesnittstatusen for værmeldingen frem i tid.
 *
 * @property locationForecast Liste over værmeldingsobjekter for en bestemt plassering. Kan være null.
 */
open class ForecastUiState(
    val locationForecast: List<WeatherForecast>?
)

/**
 * Klasse for å representere brukergrensesnittstatusen for høydevind.
 *
 * @property windyWindsList Liste over vindobjekter for forskjellige høyder. Kan være null.
 */
open class HeightWindUiState(
    val windyWindsList: List<WindyWinds>?
)

/**
 * Klasse for å representere brukergrensesnittstatusen for valgt takeoff-lokasjon. Bruker for å
 * hente vær.
 *
 * @property takeoff Valgt takeoff-lokasjon. Kan være null.
 */
open class TakeoffUiState(
    val takeoff: Takeoff?
)

/**
 * Klasse for å representere brukergrensesnittstatusen for nåværende vær for flere plasseringer.
 *
 * @property currentWeatherList Liste over CurrentWeatherUiState-objekter som representerer værdata for forskjellige plasseringer.
 */
open class MultiCurrentWeatherUiState(
    val currentWeatherList: List<CurrentWeatherUiState>
)

/**
 * Klasse for å representere brukergrensesnittstatusen for vinddata for flere plasseringer.
 *
 * @property windList Liste over vindobjekter for forskjellige plasseringer.
 */
open class LocationsWindUiState(
    val windList: List<Wind>
)
