package com.example.thrillcast.data.repositories

import com.example.thrillcast.data.datasources.WindyDataSource
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

//Dette er repository-et til Windy, her henter vi data fra WindyDataSource,
//tar ut det vi trenger og sender videre til ViewModels
class WindyRepository {

    //Setter opp et WindyDataSource-objekt for å gjøre kall
    private val windyDataSource: WindyDataSource = WindyDataSource()

    //Henter data fra Windy for angitt lokasjon med lat og lon, returnerer en liste med WindyWinds.
    //WindyWinds er et objekt vi har laget selv for å samle all værdata for de
    // ulike høydene vi ønsker å fremstille, det finnes da ett objekt for hver timestamp vi får med
    //data for alle fire ulike høyder.
    suspend fun fetchWindyWindsList(lat: Double, lng: Double): List<WindyWinds> {
        val windyObject = windyDataSource.fetchWindyObject(lat, lng)

        val timestamps = windyObject?.ts ?: listOf<Long>(0)
        val windU950h  = windyObject?.windU950h ?: listOf<Double>(0.0)
        val windU900h  = windyObject?.windU900h ?: listOf<Double>(0.0)
        val windU850h  = windyObject?.windU850h ?: listOf<Double>(0.0)
        val windU800h  = windyObject?.windU800h ?: listOf<Double>(0.0)
        val windV950h  = windyObject?.windV950h ?: listOf<Double>(0.0)
        val windV900h  = windyObject?.windV900h ?: listOf<Double>(0.0)
        val windV850h  = windyObject?.windV850h ?: listOf<Double>(0.0)
        val windV800h  = windyObject?.windV800h ?: listOf<Double>(0.0)

        val windyWindsList: MutableList<WindyWinds> = mutableListOf()

        timestamps?.forEachIndexed { index, timestamp ->
            windyWindsList.add(
                WindyWinds(
                    time = timestamp,
                    speedDir800h = calculateWindSpeedAndDirection(windU800h[index], windV800h[index]),
                    speedDir850h = calculateWindSpeedAndDirection(windU850h[index], windV850h[index]),
                    speedDir900h = calculateWindSpeedAndDirection(windU900h[index], windV900h[index]),
                    speedDir950h = calculateWindSpeedAndDirection(windU950h[index], windV950h[index])
                )
            )
        }

        return windyWindsList
    }

    //Slettet 'private' for å kjøre enhetstest

    /*
    Vindhastighet og retning defineres av en todimensjonal vektor. Parameteret u definerer hastigheten
    til en vind som blåser fra vest mot øst (en negativ verdi antyder derfor motsatt retning).
    Parameteret v definerer tilsvarende hastigheten til en vind som blåser fra sør mot nord.

    Denne funksjonen bruker vi for å regne ut vindhastighet- og retning ut ifra verdiene vi får,
    vi returner da en pair der first er windhastighet og second er vindretning.
     */
    fun calculateWindSpeedAndDirection(u: Double, v: Double): Pair<Double, Double> {
        val windSpeed = sqrt(u.pow(2) + v.pow(2))
        val windDirection = atan2(v, u) * 180 / PI
        return Pair(windSpeed, windDirection)
    }
}