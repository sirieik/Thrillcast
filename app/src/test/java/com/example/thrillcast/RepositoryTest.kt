package com.example.thrillcast

import com.example.thrillcast.data.repositories.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDate

//enhetstesting
class RepositoryTest {

    @Test
    fun testCalculateWindSpeedNDirection(){
        //Arrange
        val u = 10.0
        val v = 10.0

        //Act
        val result = Repository().calculateWindSpeedAndDirection(u, v)

        //Assert
        assertEquals(14.14, result.first, 0.01)
        assertEquals(45.0, result.second, 0.01)
    }

    @Test
    fun fetchMetWeatherForecastIsTomorrow(){
        //Arrange
        val lat = 59.76
        val lon = 10.04
        val tomorrowDate = LocalDate.now().plusDays(1)

        //Act
        val result = runBlocking {
            Repository().fetchMetWeatherForecast(lat,lon)
        }

        //Assert
        result.forEach {
            assertEquals(it.time.toLocalDate(), tomorrowDate)
        }
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
            Repository().fetchMetWeatherForecast(lat,lon)
        }

        //Assert
        val forecast = result.first()
        assertNotNull(forecast.data?.instant?.details?.air_temperature)
        assertNotNull(forecast.data?.instant?.details?.wind_speed)
        assertNotNull(forecast.data?.next_1_hours?.summary?.symbol_code)
    }
    /*
      suspend fun fetchTakeoffs(): List<com.example.thrillcast.ui.viemodels.map.Takeoff> {
     */
    @Test
    fun takeOffListIsNotEmpty(){
        //Act
        val fetchTakeoffsResult = runBlocking{
            Repository().fetchTakeoffs()
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
            Repository().fetchHolfuyObjects()
        }
        //Assert
        assertTrue(holfuyObjectResult.isNotEmpty())
    }

}