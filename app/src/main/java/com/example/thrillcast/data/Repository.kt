package com.example.thrillcast.data

import HolfuyModel
import HolfuyObject
import MapModel
import MetModel
import Takeoff
import Wind
import WindyModel
import WindyObject
import com.google.android.gms.maps.model.LatLng

/**
 * The main responsibility in our repository class is to fetch and manipulate data. The objects are created in their respective
 * classes, but in the repository, the objects are put together.
 */
class Repository {

    private val holfuyModel: HolfuyModel = HolfuyModel()
    private val mapModel: MapModel = MapModel()
    private val metModel: MetModel = MetModel()
    private val windyModel: WindyModel = WindyModel()
    // private val databaseClass: DatabaseModel = Databasemodel()

    /**
     * Functions we need:
     * To fetch data
     * To (if needed) manipulate data
     *
     * We make the functions suspend functions so that we can retrieve the data in a concurrent manner.
     */

    /**
     * Retrieving holfuy objects: retrieve one and one objects using holfuyID provided by the database.
     * Put them together in a list of holfuyObjects.
     */

    private val stations = listOf("stationName1", "stationName2")

    suspend fun fetchHolfuyObjects(): List<HolfuyObject> {
        var holfuyObjects = listOf<HolfuyObject>()
        for (item: String in stations) {
            holfuyObjects += holfuyModel.fetchHolfuyObject(item)
        }
        return holfuyObjects;
    }

    /**
     * This function is possibly not needed.
     */
    suspend fun getMapObjects() {
    }

    suspend fun getMetObjects() {
    }

    /**
     * Retrieving windy objects: retrieve one and one objects using coordinates provided by the database.
     * Put them together in a list of windyObjects.
     */
    suspend fun fetchWindyObjects(): List<WindyObject> {
        var windyObjects = listOf<WindyObject>()
        windyObjects += windyModel.fetchWindyObject("", "")

        return windyObjects;
    }

    /**
     * Functions for retrieving data from a database:


    // Function to get data in NotesDatabase
    suspend fun getTakeOffSpots() : List<TakeOffSpot> {
    return databaseClass.getSpots()
    }

    // Function to manipulate data in the database (add data)
    suspend fun addSpots(spot: TakeOffSpot): List<TakeOffSpot> {
    databaseClass.addSpots(spot)
    return getSpots()
    }

    // Function to manipulate data in database (deletes all entries of spots)
    suspend fun deleteAll(): List<TakeOffSpot> {
    databaseClass.deleteAll()
    return getSpots()
    }
     */


    //Funksjon for å sette steder på kart uten database
    suspend fun fetchStationLatLngAndNames(): HashMap<String, LatLng> {
        var stations = holfuyModel.fetchHolfuyStations()
        var stationsInNor = stations.filter { it.location?.countryCode == "NO" }
        var namesAndLatLng: HashMap<String, LatLng> = hashMapOf()

        stationsInNor.forEach {
            val name = it.name
            val latLng = it.location?.latitude?.let { it1 -> it.location?.longitude?.let { it2 ->
                LatLng(it1,
                    it2
                )
            } }
            if (name != null && latLng != null) {
                namesAndLatLng.put(name, latLng)
            }

        }


        return namesAndLatLng
    }

    suspend fun fetchTakeoffs(): List<Takeoff> {
        var stations = holfuyModel.fetchHolfuyStations()
        var stationsInNor = stations.filter { it.location?.countryCode == "NO" }
        var takeoffs: MutableList<Takeoff> = mutableListOf()

        stationsInNor.forEach {
            val name = it.name
            val latLng = it.location?.latitude?.let { it1 -> it.location?.longitude?.let { it2 ->
                LatLng(it1,
                    it2
                )
            } }
            val id = it.id
            val greenStart = it.directionZones?.green?.start
            val greenStop = it.directionZones?.green?.stop
            val moh = it.location?.altitude
            if (name != null && latLng != null && id != null && greenStart != null && greenStop != null) {
                takeoffs.add(
                    Takeoff(
                        id = id,
                        name = name,
                        coordinates = latLng,
                        greenStart = greenStart,
                        greenStop = greenStop,
                        moh = moh!!
                    )
                )
            }

        }

        return takeoffs
    }
    suspend fun fetchHolfuyStationWeather(id: Int): Wind? {
        val holfObject = holfuyModel.fetchHolfuyObject("$id")
        return holfObject.wind
    }

}