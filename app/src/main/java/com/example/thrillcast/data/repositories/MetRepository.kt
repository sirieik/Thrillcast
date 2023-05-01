
import com.example.thrillcast.data.datasources.MetDataSource

import java.time.LocalDate

class MetRepository {

    private val metDataSource: MetDataSource = MetDataSource()

    suspend fun fetchMetWeatherForecast(lat:Double, lon:Double): List<WeatherForecast> {
        val metObject = metDataSource.fetchLocationForecastObject(lat, lon)
        //val tomorrowsDate = LocalDate.now().plusDays(1)

        return metObject.properties.timeseries
        //.filter { it.time.toLocalDate() == tomorrowsDate }
    }

    suspend fun fetchLocationForecast(lat:Double, lon:Double): List<WeatherForecast> {
        val metObject = metDataSource.fetchLocationForecastObject(lat, lon)
        return metObject.properties.timeseries
    }

    suspend fun fetchNowCastObject(lat: Double, lon: Double): MetObject {
        return metDataSource.fetchNowCastObject(lat, lon)
    }

}