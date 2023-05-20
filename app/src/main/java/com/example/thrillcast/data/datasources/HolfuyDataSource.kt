package com.example.thrillcast.data.datasources

import com.example.thrillcast.data.datamodels.HolfuyObject
import com.example.thrillcast.data.datamodels.HolfuyStations
import com.example.thrillcast.data.datamodels.StationList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*
import javax.inject.Inject

/**
 * En singleton dataSource som er ansvarlig for å hente og parse
 * værdata fra APIet til Holfuy. Den håndterer også feil ved API-kall.
 * Bruker Ktor for å både hente og gjøre JSON-data om til objekter.
 */
class HolfuyDataSource @Inject constructor(){

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

    /**
     * Henter værdata for en spesifikk værstasjon gitt ved "station" parameteren.
     *
     * @param station ID-en til værstasjonen.
     * @return HolfuyObject som inneholder værdata for værstasjonen, eller null hvis feil oppstår.
     */
    suspend fun fetchHolfuyObject(station: String): HolfuyObject? {
        return try {
            client.get(path + station + endOfPath).body()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Henter en liste over alle Holfuy-stasjoner med tilhørende informasjon.
     *
     * @return Liste over com.example.thrillcast.data.datamodels.HolfuyStations som inneholder informasjon om hver stasjon,
     *         eller null hvis feil oppstår.
     */
    suspend fun fetchHolfuyStations(): List<HolfuyStations>? {
        return try {
            client.get("https://api.holfuy.com/stations/stations.json").body<StationList>().holfuyStationsList
        } catch (e: Exception) {
            null
        }
    }

}