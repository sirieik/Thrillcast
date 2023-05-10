package com.example.thrillcast

import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.ui.screens.mapScreen.MarkerIconResource
import com.example.thrillcast.ui.viemodels.map.Takeoff
import com.google.android.gms.maps.model.LatLng
import org.junit.Test
import org.junit.Assert.*

class MapScreenContentTest {

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
