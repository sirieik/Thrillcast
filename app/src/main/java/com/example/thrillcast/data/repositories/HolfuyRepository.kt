package com.example.thrillcast.data.repositories

import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.datasources.HolfuyDataSource
import com.example.thrillcast.ui.viemodels.map.Takeoff
import com.google.android.gms.maps.model.LatLng

//Dette er repository-et til Holfuy, her henter vi data fra HolfuyDataSource,
//tar ut det vi trenger og sender videre til ViewModels
class HolfuyRepository {

    //Setter opp et HolfuyDataSource objekt for å gjøre kall
    private val holfuyDataSource: HolfuyDataSource = HolfuyDataSource()


    //Her skal vi sette opp databasen
    /*
    private val db = Room.databaseBuilder(
            context,
            StationsDatabase::class.java, "stations-database"
            ).build()


     */

    //Her henter vi inn stasjonene fra Holfuy
    suspend fun fetchTakeoffs(): List<Takeoff> {
        val stations = holfuyDataSource.fetchHolfuyStations()

        //Vi sorterer ut kun de takeofflokasjonene som er i Norge
        val stationsInNor = stations.filter { it.location.countryCode == "NO" }

        //Initierer en mutableList for alle takeofflokasjonene
        val takeoffs: MutableList<Takeoff> = mutableListOf()


        //Her oppretter vi Takeoff-objekter for hver lokasjon i listen med kun norske stasjoner
        //og legger de til i listen over.
        stationsInNor.forEach {
            val name = it.name
            val latLng = it.location.latitude.let { it1 ->
                it.location.longitude.let { it2 ->
                    LatLng(it1, it2)
                }
            }
            val id = it.id
            val greenStart = it.directionZones.green.start
            val greenStop = it.directionZones.green.stop
            val moh = it.location.altitude
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

    //Denne skal brukes for å prepopulate databasen vår
/*
    suspend fun fillDatabase(){
        val stationDao = db.stationsDao()
        val stationList = fetchTakeoffs()
        for (item in stationList){
            val station = Station(
                item.id,
                item.name,
                item.coordinates.latitude,
                item.coordinates.longitude,
                item.moh,
                item.greenStart,
                item.greenStop,
                false
            )
            stationDao.upsertStation(station)
        }
    }

 */

    //Her henter vi værdata for valgt stasjon angitt ved "id"
    suspend fun fetchHolfuyStationWeather(id: Int): Wind {
        val holfObject = holfuyDataSource.fetchHolfuyObject("$id")
        return holfObject.wind
    }
}