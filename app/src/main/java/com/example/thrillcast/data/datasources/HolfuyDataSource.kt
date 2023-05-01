package com.example.thrillcast.data.datasources

import com.example.thrillcast.data.datamodels.HolfuyObject
import HolfuyStations
import StationList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

//Her henter vi data fra Holfuy
class HolfuyDataSource {

    //Holfuy APInøkkel
    private val apiKey = "zFIU9XHEarYLxHN"

    //Holfuy API-sti
    private val path = "http://api.holfuy.com/live/?s="

    //Slutten av API-stien - vi kan gjøre kall på ulike stasjonen fra parameteren
    private val endOfPath = "&pw=$apiKey&m=JSON&tu=C&su=m/s"

    //Setter opp HTTPklienten
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    //Hente værdata for én værstasjon gitt av parameter "station", her sender vi med en ID
    suspend fun fetchHolfuyObject(station: String): HolfuyObject {
        return client.get(path + station + endOfPath).body()
    }

    //Hente liste over alle stasjonene med tilhørende informasjon
    suspend fun fetchHolfuyStations(): List<HolfuyStations> {
        val stations: StationList = client.get("https://api.holfuy.com/stations/stations.json").body()
        return stations.holfuyStationsList
    }

}