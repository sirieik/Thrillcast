package com.example.thrillcast.data.datasources

import com.example.thrillcast.data.datamodels.MetObject
import com.google.gson.JsonDeserializer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * En singleton dataSource som er ansvarlig for å hente og parse
 * værdata fra APIet til Meteorologisk Institutt. Den håndterer også feil ved API-kall.
 * Bruker Ktor for å både hente og gjøre JSON-data om til objekter.
 */
@Singleton
class MetDataSource @Inject constructor() {

    //Stien til proxy-serveren til Meteorologisk Institutt
    private val path = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/"
    //Tittelen på headeren som må legges til
    private val header = "X-Gravitee-API-Key"
    //Vår nøkkel til APIet
    private val apiKey = "4cb78578-f2d3-4f28-a810-7b8f7582a1fb"

    //Sette opp HTTP klient
    //Legger til støtte for at den kan parse string til ZonedDateTime for videre bruk
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson(ContentType.Application.Json) {
                this.registerTypeAdapter(ZonedDateTime::class.java, JsonDeserializer { json, _, _ ->
                    ZonedDateTime.parse(json.asString)
                })
            }
        }
    }

    /**
     * Henter et objekt fra LocationForecast for en angitt plassering med breddegrad og lengdegrad.
     * Dette objektet gir værvarsel for fremtiden.
     *
     * @param lat Breddegraden til plasseringen.
     * @param lon Lengdegraden til plasseringen.
     * @return Et [MetObject] som inneholder værdata for plasseringen, eller null hvis det oppstår en feil.
     */
    suspend fun fetchLocationForecastObject(lat:Double, lon:Double): MetObject? {
        return try{
            client.get("${path}locationforecast/2.0/compact?lat=${lat}&lon=${lon}") {
                headers {
                    append(header,apiKey )
                }
            }.body()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Henter et objekt fra NowCast for en angitt plassering med breddegrad og lengdegrad.
     * Dette objektet gir værdata for akkurat nå.
     *
     * @param lat Breddegraden til plasseringen.
     * @param lon Lengdegraden til plasseringen.
     * @return Et [MetObject] som inneholder værdata for plasseringen, eller null hvis det oppstår en feil.
     */
    suspend fun fetchNowCastObject(lat:Double, lon: Double): MetObject? {
        return try {
            client.get("${path}nowcast/2.0/complete?lat=${lat}&lon=${lon}") {
                headers {
                    append(header, apiKey)
                }
            }.body()
        } catch (e: Exception) {
            null
        }
    }
}