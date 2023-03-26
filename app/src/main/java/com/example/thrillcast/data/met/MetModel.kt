import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

class MetModel() {

    //Met API key
    val apiKey = "4cb78578-f2d3-4f28-a810-7b8f7582a1fb"

    //Met API
    val path = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/"

    //Set up HTTP client
    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
    }
}