package com.example.thrillcast.data

import HolfuyModel
import HolfuyObject
import MetModel
import Takeoff
import Wind
import WindyModel
import WindyObject
import WindyWinds
import com.example.thrillcast.data.met.nowcast.NowCastObject
import com.example.thrillcast.data.met.weatherforecast.WeatherForecast
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * The main responsibility in our repository class is to fetch and manipulate data. The objects are created in their respective
 * classes, but in the repository, the objects are put together.
 */
class Repository {

    private val holfuyModel: HolfuyModel = HolfuyModel()
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


    suspend fun fetchMetWeatherForecast(lat:Double, lon:Double): List<WeatherForecast> {
        val metObject = metModel.fetchMetObject(lat, lon)
        val tomorrowsDate = LocalDate.now().plusDays(1)

        return metObject.properties.timeseries.filter { it.time.toLocalDate() == tomorrowsDate }
    }

    suspend fun fetchLocationForecast(lat:Double, lon:Double): List<WeatherForecast> {
        val metObject = metModel.fetchMetObject(lat, lon)
        return metObject.properties.timeseries
    }

    suspend fun fetchNowCastObject(lat: Double, lon: Double): NowCastObject {
        return metModel.fetchNowCastObject(lat, lon)
    }

    suspend fun fetchWindyWindsList(lat: String, lng: String): List<WindyWinds> {
        val windyObject = windyModel.fetchWindyObject(lat, lng)

        val timestamps = windyObject.ts
        val windU950h  = windyObject.windU950h
        val windU900h  = windyObject.windU900h
        val windU850h  = windyObject.windU850h
        val windU800h  = windyObject.windU800h
        val windV950h  = windyObject.windV950h
        val windV900h  = windyObject.windV900h
        val windV850h  = windyObject.windV850h
        val windV800h  = windyObject.windV800h

        val windyWindsList: MutableList<WindyWinds> = mutableListOf()

        timestamps.forEachIndexed { index, timestamp ->
            windyWindsList.add(
                WindyWinds(
                    time = timestamp,
                    speedDir800h = calculateWindSpeedAndDirection(windU800h[index], windV800h[index]),
                    speedDir850h = calculateWindSpeedAndDirection(windU850h[index], windV850h[index]),
                    speedDir900h = calculateWindSpeedAndDirection(windU900h[index], windV900h[index]),
                    speedDir950h = calculateWindSpeedAndDirection(windU950h[index], windV950h[index])
                )
            )
        }

        return windyWindsList
    }

    //Delete 'private' before fun, in order to use for unittest
    fun calculateWindSpeedAndDirection(u: Double, v: Double): Pair<Double, Double> {
        val windSpeed = sqrt(u.pow(2) + v.pow(2))
        val windDirection = atan2(v, u) * 180 / PI
        return Pair(windSpeed, windDirection)
    }
}