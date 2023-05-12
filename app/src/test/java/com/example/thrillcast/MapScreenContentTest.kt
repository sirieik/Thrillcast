package com.example.thrillcast

import checkWindConditions
import com.example.thrillcast.ui.common.WindCondition
import org.junit.Test
import org.junit.Assert.*

class MapScreenContentTest {

    /*test1
fun MarkerIcon(wind: Wind, takeoff: Takeoff): BitmapDescriptor {
    return if (isDegreeBetween((wind.direction?: 0.0).toDouble(), takeoff.greenStart, takeoff.greenStop)) {
        BitmapDescriptorFactory.fromResource(R.drawable.greendot)
    } else {
        BitmapDescriptorFactory.fromResource(R.drawable.red_dot)
    }
}
//ny test
fun checkWindConditions(windDirection: Double?, windSpeed: Double?, greenStart: Int, greenStop: Int): WindCondition {
    return if (isDegreeBetween(windDirection ?: 0.0, greenStart, greenStop) && (windSpeed ?: 0.0) in 3.0..5.0) {
        WindCondition.GOOD
    } else if (isDegreeBetween(windDirection ?: 0.0, greenStart, greenStop) && (windSpeed ?: 0.0) in 2.0..6.0) {
        WindCondition.OKAY
    } else {
        WindCondition.BAD
    }
}
 */
    //for å sjekke at funskjon finner riktig farge av marker
    @Test
    fun testCheckWindConditions() {
        //Arrange
        val greenStart = 40
        val greenStop = 90

        //Act
        val goodWind = checkWindConditions(60.0, 5.0,  greenStart, greenStop )
        val okayWind = checkWindConditions(70.0, 6.0, greenStart, greenStop)
        val badWind = checkWindConditions(88.0, 8.0, greenStart, greenStop)
        val badWind2 = checkWindConditions(120.0, 3.0, greenStart, greenStop)

        //Assert
        assertEquals(WindCondition.GOOD, goodWind)
        assertEquals(WindCondition.OKAY, okayWind)
        assertEquals(WindCondition.BAD, badWind)
        assertEquals(WindCondition.BAD, badWind2)

    }
}

    /*

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
*/