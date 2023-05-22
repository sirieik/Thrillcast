package com.example.thrillcast.ui.common

import com.google.android.gms.maps.model.LatLng

/**
 * En dataklasse som representerer en takeoff-lokasjon.
 * Den inneholder detaljer som ID, koordinater, navn, start- og stoppverdi for det grønne området i vindhjulet,
 * og høyde over havet.
 *
 * @param id Unik identifikator for takeoff-lokasjonen.
 * @param coordinates Geografiske koordinater for takeoff-lokasjonen, representert som et LatLng-objekt.
 * @param name Navnet på takeoff-lokasjonen.
 * @param greenStart Startverdien for den grønne sonen i vindretningssirkelen.
 * @param greenStop Sluttverdien for den grønne sonen i vindretningssirkelen.
 * @param moh Høyden over havet på takeoff-lokasjonen, representert i meter.
 */
data class Takeoff(
    val id: Int,
    val coordinates: LatLng,
    val name: String,
    val greenStart: Int,
    val greenStop: Int,
    val moh : Int,
)