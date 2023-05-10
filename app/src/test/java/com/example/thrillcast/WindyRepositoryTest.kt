package com.example.thrillcast

import com.example.thrillcast.data.repositories.WindyRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

class WindyRepositoryTest {
    @Test
    fun testfetchWindyWindList(){
        //Arrange
        val lat = 59.76
        val lng = 10.04
        val today = LocalDate.now()

        //Act
        val result = runBlocking {
            WindyRepository().fetchWindyWindsList(lat, lng)
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
        Assert.assertEquals(14.14, result.first, 0.01)
        Assert.assertEquals(45.0, result.second, 0.01)
    }

    @Test
    fun testfetchWindyWindListIsNotEmpty(){
        //Arrange
        val lat = 59.76
        val lng = 10.04

        //Act
        val result = runBlocking {
            WindyRepository().fetchWindyWindsList(lat, lng)
        }

        //Assert
        Assert.assertTrue(result.isNotEmpty())
    }

}