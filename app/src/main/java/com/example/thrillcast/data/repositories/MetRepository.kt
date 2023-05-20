package com.example.thrillcast.data.repositories
import com.example.thrillcast.data.datamodels.MetObject
import com.example.thrillcast.data.datamodels.WeatherForecast
import com.example.thrillcast.data.datasources.MetDataSource

import javax.inject.Inject
import javax.inject.Singleton

//Dette er repository-et til Met, her henter vi data fra MetDataSource,
//tar ut det vi trenger og sender videre til ViewModels

@Singleton
class MetRepository @Inject constructor(){

    //Setter opp et MetDataSource-objekt for å gjøre kall
    private val metDataSource: MetDataSource = MetDataSource()

    //Henter all værdata for så langt frem i tid det går
    suspend fun fetchLocationForecast(lat:Double, lon:Double): List<WeatherForecast>? {
        val metObject = metDataSource.fetchLocationForecastObject(lat, lon)
        return metObject?.properties?.timeseries
    }

    //Henter værdata for akkurat nå
    suspend fun fetchNowCastObject(lat: Double, lon: Double): MetObject? {
        return metDataSource.fetchNowCastObject(lat, lon)
    }

}