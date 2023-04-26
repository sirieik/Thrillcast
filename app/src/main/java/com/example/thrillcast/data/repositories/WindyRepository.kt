import com.example.thrillcast.data.datasources.WindyDataSource
import com.example.thrillcast.data.repositories.WindyWinds
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class WindyRepository {

    private val windyDataSource: WindyDataSource = WindyDataSource()

    suspend fun fetchWindyWindsList(lat: Double, lng: Double): List<WindyWinds> {
        val windyObject = windyDataSource.fetchWindyObject(lat, lng)

        val timestamps = windyObject.ts
        val windU950h  = windyObject.windU950h
        val windU900h  = windyObject.windU900h
        val windU850h  = windyObject.windU850h
        val windU800h  = windyObject.windU800h
        val windV950h  = windyObject.windV950h
        val windV900h  = windyObject.windV900h
        val windV850h  = windyObject.windV850h
        val windV800h  = windyObject.windV800h

        val windyWindsList: MutableList<WindyWinds> = mutableListOf()

        timestamps.forEachIndexed { index, timestamp ->
            windyWindsList.add(
                WindyWinds(
                    time = timestamp,
                    speedDir800h = calculateWindSpeedAndDirection(windU800h[index], windV800h[index]),
                    speedDir850h = calculateWindSpeedAndDirection(windU850h[index], windV850h[index]),
                    speedDir900h = calculateWindSpeedAndDirection(windU900h[index], windV900h[index]),
                    speedDir950h = calculateWindSpeedAndDirection(windU950h[index], windV950h[index])
                )
            )
        }

        return windyWindsList
    }

    //Delete 'private' before fun, in order to use for unittest
    fun calculateWindSpeedAndDirection(u: Double, v: Double): Pair<Double, Double> {
        val windSpeed = sqrt(u.pow(2) + v.pow(2))
        val windDirection = atan2(v, u) * 180 / PI
        return Pair(windSpeed, windDirection)
    }
}