package com.example.thrillcast

import com.example.thrillcast.data.datasources.WindyDataSource
import com.example.thrillcast.data.repositories.WindyRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class WindyRepositoryTest {

/**
 * Denne testfunksjonen verifiserer funksjonaliteten til `fetchWindyWindsList` metoden i `WindyRepository`.
 *
 * Metoden forventes å hente en liste med vinddataobjekter fra en spesifikk bredde- og lengdegrad,
 * der hvert objekts tidspunkt ikke er tidligere enn starten av den nåværende dagen, og hver objekts vindhastighet/retning data
 * (på både 800h og 900h høyder) faller innenfor området -200 og 200.
 */
@Test
    fun testfetchWindyWindList(){
        //Arrange
        val lat = 59.76
        val lng = 10.04
        val today = LocalDate.now()

        //Act
        val result = runBlocking {
            WindyRepository(WindyDataSource()).fetchWindyWindsList(lat, lng)
        }
        //Assert
        result.forEach {
            Assert.assertTrue(it.time >= today.atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
            Assert.assertTrue(it.speedDir800h.first > -200 && it.speedDir800h.first < 200)
            Assert.assertTrue(it.speedDir800h.second > -200 && it.speedDir800h.second < 200)
            Assert.assertTrue(it.speedDir900h.first > -200 && it.speedDir900h.first < 200)
            Assert.assertTrue(it.speedDir900h.second > -200 && it.speedDir900h.second < 200)
        }
    }

    /**
     * Sjekker at calculateWindSpeedAndDirection-funksjonen gir riktige resultater
     */
    @Test
    fun testCalculateWindSpeedNDirection() {
        //Arrange
        val u = 10.0
        val v = 10.0

        //Act
        val result = runBlocking {
            WindyRepository(WindyDataSource()).calculateWindSpeedAndDirection(u,v)
        }

        //Assert
        Assert.assertEquals(14.14, result.first, 0.01)
        Assert.assertEquals(45.0, result.second, 0.01)
    }

    /**
     * Sjekker at fetchWindyWindsList klarer å hente objekter
     */
    @Test
    fun testfetchWindyWindListIsNotEmpty(){
        //Arrange
        val lat = 59.76
        val lng = 10.04

        //Act
        val result = runBlocking {
            WindyRepository(WindyDataSource()).fetchWindyWindsList(lat, lng)
        }

        //Assert
        Assert.assertTrue(result.isNotEmpty())
    }

}