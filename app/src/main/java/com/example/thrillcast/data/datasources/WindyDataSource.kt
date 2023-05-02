package com.example.thrillcast.data.datasources

import com.example.thrillcast.data.datamodels.WindyObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

class WindyDataSource {

    //Windy API key
    private val apiKey = "ZNn24b3G6rq28A1xMOmRFHJ6YYmzv45C"

    //Windy API sti
    private val path = "https://api.windy.com/api/point-forecast/v2"

    //Sette opp HTTP klient
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    //Her henter vi vinddata for angitt punkt i koordinater, lat og lon.
    suspend fun fetchWindyObject(lat : Double, lon : Double): WindyObject {

        //Dette er body-en vi sender med i post requesten til windy.
        //Her legger vi ved koordinatene vi tar i parameterene til funksjonen,
        //"model : iconEU" dekker Europa og omkransende områder
        //"parameters : [wind]" henter inn vindstyrke- og retning
        //"levels : ["950h", "900h", "850h", "800h"]" henter inn høydevindstyrke- og retning
        // for omtrentlig hver 500. meter
        //"key" tar imot nøkkelen til APIet

        val stringBody =
            """
            {
                "lat": $lat,
                "lon": $lon,
                "model": "iconEu",
                "parameters": ["wind"],
                "levels": ["950h", "900h", "850h", "800h"],
                "key": "$apiKey"
            }
            """

        //Her gjør vi om stringen over til TextContent slik at det kan sendes med i POST-kallet under
        val body = TextContent(stringBody, ContentType.Application.Json)


        //Her henter vi data og parser responsen til WindyObject
        val response: WindyObject = client.post(path) {
            setBody(body)
        }.body()

        return response
    }
}