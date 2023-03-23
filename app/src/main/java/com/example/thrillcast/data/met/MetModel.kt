import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*

class MetModel() {

    val apiKey = "4cb78578-f2d3-4f28-a810-7b8f7582a1fb"

    val path = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/"

    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson()
        }
    }

}