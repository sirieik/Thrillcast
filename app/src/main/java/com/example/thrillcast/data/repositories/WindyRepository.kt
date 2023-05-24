package com.example.thrillcast.data.repositories

import com.example.thrillcast.data.datasources.WindyDataSource
import com.example.thrillcast.ui.common.WindyWinds
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Singleton WindyRepository klasse ansvarlig for å håndtere henting av værdata fra WindyDataSource
 * og sende videre til ViewModel. Den sorterer også og omregner dataen.
 *
 * @constructor Initialiserer en WindyRepository instans med en injisert WindyDataSource.
 */
@Singleton
class WindyRepository @Inject constructor(private val windyDataSource: WindyDataSource) {

    /**
     * Henter vinddata fra Windy for angitt lokasjon, og returnerer en liste av WindyWinds objekter.
     * Hvert WindyWinds objekt representerer vinddata for forskjellige høyder på et bestemt tidspunkt.
     *
     * @param lat Breddegrad for stedet å hente vinddata for.
     * @param lng Lengdegrad for stedet å hente vinddata for.
     * @return En liste av WindyWinds objekter, eller en tom liste hvis ingen data er tilgjengelig.
     */
    suspend fun fetchWindyWindsList(lat: Double, lng: Double): List<WindyWinds> {
        val windyObject = windyDataSource.fetchWindyObject(lat, lng)

        val timestamps = windyObject?.ts ?: emptyList()
        val windU950h  = windyObject?.windU950h ?: emptyList()
        val windU900h  = windyObject?.windU900h ?: emptyList()
        val windU850h  = windyObject?.windU850h ?: emptyList()
        val windU800h  = windyObject?.windU800h ?: emptyList()
        val windV950h  = windyObject?.windV950h ?: emptyList()
        val windV900h  = windyObject?.windV900h ?: emptyList()
        val windV850h  = windyObject?.windV850h ?: emptyList()
        val windV800h  = windyObject?.windV800h ?: emptyList()

        return timestamps.mapIndexed { index, timestamp ->
            WindyWinds(
                time = timestamp,
                speedDir800h = calculateWindSpeedAndDirection(windU800h.getOrNull(index) ?: 0.0, windV800h.getOrNull(index) ?: 0.0),
                speedDir850h = calculateWindSpeedAndDirection(windU850h.getOrNull(index) ?: 0.0, windV850h.getOrNull(index) ?: 0.0),
                speedDir900h = calculateWindSpeedAndDirection(windU900h.getOrNull(index) ?: 0.0, windV900h.getOrNull(index) ?: 0.0),
                speedDir950h = calculateWindSpeedAndDirection(windU950h.getOrNull(index) ?: 0.0, windV950h.getOrNull(index) ?: 0.0)
            )
        }
    }

    //Slettet 'private' for å kjøre enhetstest

    /**
     * Beregner vindhastighet og retning basert på u- og v-verdiene.
     * U-verdien representerer vindens hastighet fra vest til øst, og v-verdien representerer vindens hastighet fra sør til nord.
     *
     * @param u U-verdien som representerer vindens hastighet fra vest til øst.
     * @param v V-verdien som representerer vindens hastighet fra sør til nord.
     * @return En Pair der første element er vindhastighet og andre element er vindretning.
     */
    fun calculateWindSpeedAndDirection(u: Double, v: Double): Pair<Double, Double> {
        val windSpeed = sqrt(u.pow(2) + v.pow(2))
        val windDirection = atan2(v, u) * 180 / PI
        return Pair(windSpeed, windDirection)
    }
}