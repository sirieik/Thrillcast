import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

class HolfuyModel() {

    val apiKey = "zFIU9XHEarYLxHN"

    val path = "http://api.holfuy.com/live/"

    var station = "101"

    val fullPath = "http://api.holfuy.com/live/?s=" + station + "&pw=" + apiKey + "&m=JSON&tu=C&su=m/s"

    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
    }

    //NB - might want to directly put parameter s into the query in the method, instead of changing the
    //local variable "station" for each time

    //Retrieve information about one single weather station given by parameter "s"
    suspend fun fetchHolfuyObject(s: String) : HolfuyObject {
        station = s
        val holfuyObject: HolfuyObject = client.get(fullPath).body()
        return holfuyObject
    }



}