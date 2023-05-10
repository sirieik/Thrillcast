
package com.example.thrillcast.data.repositories

import android.util.Log
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.datasources.HolfuyDataSource
import com.example.thrillcast.ui.viemodels.map.Takeoff
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

//Dette er repository-et til Holfuy, her henter vi data fra HolfuyDataSource,
//tar ut det vi trenger og sender videre til ViewModels

@Singleton
class HolfuyRepository @Inject constructor() {

    //Setter opp et HolfuyDataSource objekt for å gjøre kall
    private val holfuyDataSource: HolfuyDataSource = HolfuyDataSource()


    //Her henter vi inn stasjonene fra Holfuy
    /**
     * Hei
     * @param hallo
     * @return List<Takeoff>
     */
    suspend fun fetchTakeoffs(): List<Takeoff> {
        val stations = holfuyDataSource.fetchHolfuyStations()

        //Vi sorterer ut kun de takeofflokasjonene som er i Norge
        val stationsInNor = stations?.filter { it.location?.countryCode == "NO" }

        val takeoffs: MutableList<Takeoff> = mutableListOf()


        //Her oppretter vi Takeoff-objekter for hver lokasjon i listen med kun norske stasjoner
        //og legger de til i listen over.
        stationsInNor?.forEach {
            val name = it.name ?: "IFI"
            val latLng = LatLng(
                it.location?.latitude ?: 58.88083,
                it.location?.longitude ?: 9.01861
            )

            val id = it.id ?: 0
            val greenStart = it.directionZones?.green?.start ?: 0
            val greenStop = it.directionZones?.green?.stop ?: 0
            val moh = it.location?.altitude ?: 0
            takeoffs.add(
                Takeoff(
                    id = id,
                    name = name,
                    coordinates = latLng,
                    greenStart = greenStart,
                    greenStop = greenStop,
                    moh = moh
                )
            )
        }
        return takeoffs
    }

    //Her henter vi værdata for valgt stasjon angitt ved "id"
    suspend fun fetchHolfuyStationWeather(id: Int): Wind? {
        val holfObject = holfuyDataSource.fetchHolfuyObject("$id")
        return holfObject?.wind
    }
}