package com.example.thrillcast.ui.viemodels.map

import com.google.android.gms.maps.model.LatLng

data class Takeoff(
    val id: Int,
    val coordinates: LatLng,
    val name: String,
    val greenStart: Int,
    val greenStop: Int,
    val moh : Int,
    val isFavorite : Boolean = false

)