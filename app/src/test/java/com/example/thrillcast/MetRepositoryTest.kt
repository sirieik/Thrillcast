package com.example.thrillcast

import com.example.thrillcast.data.datasources.MetDataSource
import com.example.thrillcast.data.repositories.MetRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class MetRepositoryTest {

    /**
     * Denne testfunksjonen bekrefter `fetchLocationForecast` metoden i `MetRepository`.
     *
     * Metoden forventes å returnere en ikke-tom liste over værprognosedata for den angitte bredde- og lengdegraden for neste dag.
     *
     */

    @Test
    fun fetchMetWeatherForecastTomorrow(){
        //Arrange
        val lat = 59.76
        val lon = 10.04
        val tomorrowDate = LocalDate.now().plusDays(1)

        //Act
        val result = runBlocking {
            MetRepository(MetDataSource()).fetchLocationForecast(lat,lon)
        }
        val tomorrow = result?.filter { it.time?.toLocalDate() == tomorrowDate }

        //Assert
        Assert.assertTrue(!tomorrow.isNullOrEmpty())
    }

    /**
     * Denne testfunksjonen bekrefter `fetchLocationForecast` metoden i `MetRepository`.
     *
     * Metoden forventes å returnere værprognosedata for den angitte bredde- og lengdegraden, der
     * visse elementer (lufttemperatur, vindhastighet og symbolkode for neste time) ikke er null.
     */
    @Test
    fun weatherForecastDataIsNotNull(){
        // Arrange
        val lat = 59.76
        val lon = 10.04

        //Act
        val result = runBlocking {
            MetRepository(MetDataSource()).fetchLocationForecast(lat,lon)
        }

        //Assert
        val forecast = result?.first()
        Assert.assertNotNull(forecast?.data?.instant?.details?.air_temperature)
        Assert.assertNotNull(forecast?.data?.instant?.details?.wind_speed)
        Assert.assertNotNull(forecast?.data?.next_1_hours?.summary?.symbol_code)
    }

    /**
     * Denne testfunksjonen bekrefter `fetchLocationForecast` metoden i `MetRepository`.
     *
     * Metoden forventes å returnere værprognosedata for den angitte bredde- og lengdegraden,
     * hvor hver prognoses lufttemperatur er mellom -100 og 100, og vindhastigheten er mellom 0 og 200.
     *
     */
    @Test
    fun weatherForecastDataIsExpected() {
        // Arrange
        val lat = 59.76
        val lon = 10.04

        //Act
        val result = runBlocking {
            MetRepository(MetDataSource()).fetchLocationForecast(lat, lon)
        }

        //Assert
        result?.forEach {
            val temp = it.data?.instant?.details?.air_temperature!!
            val windSpeed = it.data?.instant?.details?.wind_speed!!

            Assert.assertTrue(temp > -100 && temp < 100)
            Assert.assertTrue(0 <= windSpeed && windSpeed < 200)
        }
    }

}