import com.example.thrillcast.data.met.MetObject
import com.google.gson.JsonDeserializer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MetModel() {

    //Met API key
    //val apiKey = "4cb78578-f2d3-4f28-a810-7b8f7582a1fb"
    //Met API
    //val path = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/"
    //Met API med eksempel lat og lon
    //val path2 = "https://api.met.no/weatherapi/locationforecast/2.0/compact"
    //"https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=60.10&lon=9.58"

    //NEW with Proxy
    //https://gw-uio.intark.uh-it.no/in2000/weatherapi/locationforecast/2.0/compact?lat=60.10&lon=9.58
    val path2 = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/locationforecast/2.0/compact"

    //Set up HTTP client
    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson(ContentType.Application.Json) {
                this.registerTypeAdapter(ZonedDateTime::class.java, JsonDeserializer { json, _, _ ->
                    ZonedDateTime.parse(json.asString)
                })
            }
        }
    }
    suspend fun fetchMetObject(lat:Double, lon:Double): MetObject{
        return client.get("${path2}?lat=${lat}&lon=${lon}") {
            headers {
                append("X-Gravitee-API-Key","4cb78578-f2d3-4f28-a810-7b8f7582a1fb" )
            }
        }.body()
    }


}