import android.util.Log
import com.example.thrillcast.data.datasources.MetDataSource
import com.example.thrillcast.data.met.nowcast.NowCastObject

import java.time.LocalDate

class MetRepository {

    private val metDataSource: MetDataSource = MetDataSource()

    suspend fun fetchMetWeatherForecast(lat:Double, lon:Double): List<WeatherForecast> {
        val metObject = metDataSource.fetchMetObject(lat, lon)
        val tomorrowsDate = LocalDate.now().plusDays(1)

        Log.d("Hei", ${metObject.properties.timeseries[0]})

        return metObject.properties.timeseries.filter { it.time.toLocalDate() == tomorrowsDate }
    }

    suspend fun fetchLocationForecast(lat:Double, lon:Double): List<WeatherForecast> {
        val metObject = metDataSource.fetchMetObject(lat, lon)
        return metObject.properties.timeseries
    }

    suspend fun fetchNowCastObject(lat: Double, lon: Double): NowCastObject {
        return metDataSource.fetchNowCastObject(lat, lon)
    }

}