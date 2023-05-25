package com.example.thrillcast

import com.example.thrillcast.ui.common.calculations.checkWindConditions
import com.example.thrillcast.ui.common.WindCondition
import org.junit.Test
import org.junit.Assert.*

class MapScreenContentTest {

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