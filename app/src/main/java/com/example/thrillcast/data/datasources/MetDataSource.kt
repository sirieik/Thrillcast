package com.example.thrillcast.data.datasources

import MetObject
import com.google.gson.JsonDeserializer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import java.time.ZonedDateTime

//Her henter vi data fra Meterologisk Institutt
class MetDataSource {

    //Stien til proxy-serveren til meterologisk institutt
    private val path = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/"
    //Tittelen på headeren som må legges til
    private val header = "X-Gravitee-API-Key"
    //Vår nøkkel til APIet
    private val apiKey = "4cb78578-f2d3-4f28-a810-7b8f7582a1fb"

    //Sette opp HTTP klient
    //Legger til slik at den kan parse string til ZonedDateTime for videre bruk
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson(ContentType.Application.Json) {
                this.registerTypeAdapter(ZonedDateTime::class.java, JsonDeserializer { json, _, _ ->
                    ZonedDateTime.parse(json.asString)
                })
            }
        }
    }

    //Hente objekt fra LocationForecast for angitt sted med latitude og longitude, altså
    //koordinater. Denne melder været fremover i tid
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

    //Hente objekt fra NowCast for angitt sted med latitude og longitude, altså
    //koordinater. Denne melder været akkurat nå
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