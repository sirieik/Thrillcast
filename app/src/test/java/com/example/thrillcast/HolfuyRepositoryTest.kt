package com.example.thrillcast

import com.example.thrillcast.data.datasources.HolfuyDataSource
import com.example.thrillcast.data.repositories.HolfuyRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class HolfuyRepositoryTest {
    @Test
    fun takeOffListIsNotEmpty(){
        //Act
        val fetchTakeoffsResult = runBlocking{
            HolfuyRepository(HolfuyDataSource()).fetchTakeoffs()
        }
        //Assert
        Assert.assertTrue(fetchTakeoffsResult.isNotEmpty())
    }
    @Test
    fun fetchHolfuyObjectsIsNotEmpty(){
        //Act
        val holfuyObjectResult = runBlocking {
            HolfuyRepository(HolfuyDataSource()).fetchHolfuyStationWeather(586)
        }
        //Assert
        Assert.assertNotNull(holfuyObjectResult)
    }

    @Test
    //tester grønnhjul er mellom 0 til 360 grader
    fun testFetchTakeoffs(){
        //Act
        val fetchTakeoffsResult = runBlocking{
            HolfuyRepository(HolfuyDataSource()).fetchTakeoffs()
        }
        //Assert
        fetchTakeoffsResult.forEach {
            val greenStart = it.greenStart
            val greenStop = it.greenStop

            Assert.assertTrue(greenStart in 0..360)
            print("STOP: $greenStop")
            Assert.assertTrue(greenStop in 0..360)
        }
    }
}