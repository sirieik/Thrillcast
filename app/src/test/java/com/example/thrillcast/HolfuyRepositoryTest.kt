package com.example.thrillcast

import com.example.thrillcast.data.repositories.HolfuyRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class HolfuyRepositoryTest {

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
        Assert.assertTrue(fetchTakeoffsResult.isNotEmpty())
    }
    /*
     suspend fun fetchHolfuyObjects(): List<com.example.thrillcast.data.datamodels.HolfuyObject>
     */
    @Test
    //test fungerer ikke akkurat nå, pga Holfuy API fungerer ikke.
    fun fetchHolfuyObjectsIsNotEmpty(){
        //Act
        val holfuyObjectResult = runBlocking {
            HolfuyRepository().fetchHolfuyStationWeather(101)
        }
        //Assert
        Assert.assertNotNull(holfuyObjectResult)
    }

    @Test
    //tester grønnhjul er mellom 0 til 360 grader
    fun testFetchTakeoffs(){
        //Act
        val fetchTakeoffsResult = runBlocking{
            HolfuyRepository().fetchTakeoffs()
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