package com.example.thrillcast.data.datasources

import com.example.thrillcast.data.datamodels.WindyObject
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

class WindyDataSource() {

    //Windy API key
    private val apiKey = "ZNn24b3G6rq28A1xMOmRFHJ6YYmzv45C"

    //Windy API
    private val path = "https://api.windy.com/api/point-forecast/v2"

    //Set up HTTP client
    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchWindyObject(lat : Double, lon : Double): WindyObject {

        val stringBody =
            """
            {
                "lat": $lat,
                "lon": $lon,
                "model": "iconEu",
                "parameters": ["wind"],
                "levels": ["950h", "900h", "850h", "800h"],
                "key": "ZNn24b3G6rq28A1xMOmRFHJ6YYmzv45C"
            }
            """

        val body = TextContent(stringBody, ContentType.Application.Json)

        val response: WindyObject = client.post(path) {
            setBody(body)
        }.body()

        return response
    }
}