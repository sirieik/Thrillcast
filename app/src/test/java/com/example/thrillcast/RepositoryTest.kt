package com.example.thrillcast

import android.graphics.Bitmap
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.HolfuyRepository
import com.example.thrillcast.data.repositories.MetRepository
import com.example.thrillcast.data.repositories.WindyRepository
import com.example.thrillcast.ui.screens.mapScreen.MarkerIcon
import com.example.thrillcast.ui.screens.mapScreen.MarkerIconResource
import com.example.thrillcast.ui.viemodels.map.Takeoff
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDate

//enhetstesting
class RepositoryTest {

    @Test
    fun testCalculateWindSpeedNDirection() {
        //Arrange
        val u = 10.0
        val v = 10.0

        //Act
        val result = runBlocking {
                WindyRepository().calculateWindSpeedAndDirection(u,v)
        }

        //Assert
        assertEquals(14.14, result.first, 0.01)
        assertEquals(45.0, result.second, 0.01)
    }

    @Test
    fun fetchMetWeatherForecastTomorrow(){
        //Arrange
        val lat = 59.76
        val lon = 10.04
        val tomorrowDate = LocalDate.now().plusDays(1)

        //Act
        val result = runBlocking {
            MetRepository().fetchLocationForecast(lat,lon)
        }
        val tomorrow = result?.filter { it.time?.toLocalDate() == tomorrowDate }

        //Assert
        assertTrue(!tomorrow.isNullOrEmpty())
    }
    /*
    data class Details (
    var air_temperature : Double?,
    var wind_speed : Double?
        )
     */
    @Test
    fun weatherForecastDataIsNotNull(){
        // Arrange
        val lat = 59.76
        val lon = 10.04

        //Act
        val result = runBlocking {
            MetRepository().fetchLocationForecast(lat,lon)
        }

        //Assert
        val forecast = result?.first()
        assertNotNull(forecast?.data?.instant?.details?.air_temperature)
        assertNotNull(forecast?.data?.instant?.details?.wind_speed)
        assertNotNull(forecast?.data?.next_1_hours?.summary?.symbol_code)
    }
    /*
      suspend fun fetchTakeoffs(): List<com.example.thrillcast.ui.viemodels.map.Takeoff> {
     */
    @Test
    fun takeOffListIsNotEmpty(){
        //Act
        val fetchTakeoffsResult = runBlocking{
            HolfuyRepository().fetchTakeoffs()
        }
        //Assert
        assertTrue(fetchTakeoffsResult.isNotEmpty())
    }
    /*
     suspend fun fetchHolfuyObjects(): List<com.example.thrillcast.data.datamodels.HolfuyObject>
     */
    @Test
    fun fetchHolfuyObjectsIsNotEmpty(){
        //Act
        val holfuyObjectResult = runBlocking {
            HolfuyRepository().fetchHolfuyStationWeather(102)
        }
        //Assert
        assertNotNull(holfuyObjectResult)
    }
/*
fun MarkerIcon(wind: Wind, takeoff: Takeoff): BitmapDescriptor {
    return if (isDegreeBetween((wind.direction?: 0.0).toDouble(), takeoff.greenStart, takeoff.greenStop)) {
        BitmapDescriptorFactory.fromResource(R.drawable.greendot)
    } else {
        BitmapDescriptorFactory.fromResource(R.drawable.red_dot)
    }
}
 */
    //for å sjekke at funskjon finner riktig farge av marker
    @Test
    fun testMarkerIconFarge() {
    //Arrange
    val takeoff = Takeoff(
        0,
        LatLng(
            59.76,
            10.04
        ),
        "Navn",
        greenStart = 30,
        greenStop = 90,
        1200
    )
    val greenWind = Wind(50, 5.0, 20.0, 60.0, " m/s ")
    val redWind = Wind(200, 50.0, 50.0, 100.0,"m/s")

    //Act
    val greenResult = MarkerIconResource(greenWind, takeoff)
    val redResult = MarkerIconResource(redWind, takeoff)

    //Assert
    assertEquals(R.drawable.greendot, greenResult)
    assertEquals(R.drawable.red_dot, redResult)
    }

}
