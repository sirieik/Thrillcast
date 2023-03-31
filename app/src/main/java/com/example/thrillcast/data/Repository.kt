package com.example.thrillcast.data

import HolfuyModel
import HolfuyObject
import MapModel
import MetModel
import WindyModel
import WindyObject

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

}