import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

class HolfuyModel() {

    private val apiKey = "zFIU9XHEarYLxHN"

    private val path = "http://api.holfuy.com/live/"

    private val endOfPath = "&pw=$apiKey&m=JSON&tu=C&su=m/s"


    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
    }

    //Retrieve information about one single weather station given by parameter "s"
    suspend fun fetchHolfuyObject(station: String): HolfuyObject {
        return client.get(path + station + endOfPath).body()
    }

}