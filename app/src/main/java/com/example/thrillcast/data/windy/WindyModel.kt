import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

class WindyModel() {

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

    //NB: litt rart m de skråstrekene i bodyen, kanskje vi oppdager at det er feil
    //også litt usikker på om den blir parset til windyObject nå, men det oppdager vi fort når vi tester
    suspend fun fetchWindyObject(lat : String, lon : String): WindyObject {
        val windyObject: WindyObject = client.post(path) {
            setBody("{\n" +
                    "    \"lat\": $lat,\n" +
                    "    \"lon\": $lon,\n" +
                    "    \"model\": \"iconEu\",\n" +
                    "    \"parameters\": [\"wind\"],\n" +
                    "    \"levels\": [\"950h\", \"900h\", \"850h\", \"800h\"],\n" +
                    "    \"key\": \"ZNn24b3G6rq28A1xMOmRFHJ6YYmzv45C\"\n" +
                    "}")
        }.body()
        return windyObject
    }
}