package com.example.thrillcast.data.datasources

import com.example.thrillcast.data.datamodels.HolfuyObject
import HolfuyStations
import StationList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

class HolfuyDataSource() {

    //Holfuy API key
    private val apiKey = "zFIU9XHEarYLxHN"

    //Holfuy API
    private val path = "http://api.holfuy.com/live/?s="

    //End of API path - we can make calls to the different stations from the parameter
    private val endOfPath = "&pw=$apiKey&m=JSON&tu=C&su=m/s"

    //Set up HTTP client
    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
    }

    //Retrieve information about one single weather station given by parameter "s"
    suspend fun fetchHolfuyObject(station: String): HolfuyObject {
        return client.get(path + station + endOfPath).body()
    }

    //Retrieve list over stations
    suspend fun fetchHolfuyStations(): List<HolfuyStations> {
        val stations: StationList = client.get("https://api.holfuy.com/stations/stations.json").body()
        return stations.holfuyStationsList
    }

}