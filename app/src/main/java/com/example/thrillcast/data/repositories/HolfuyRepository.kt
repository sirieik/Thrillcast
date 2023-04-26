import android.content.Context
import androidx.room.Room
import com.example.thrillcast.data.database.Station
import com.example.thrillcast.data.database.StationsDatabase
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.datasources.HolfuyDataSource
import com.example.thrillcast.ui.viemodels.map.Takeoff
import com.google.android.gms.maps.model.LatLng

class HolfuyRepository(context: Context){

    private val holfuyDataSource: HolfuyDataSource = HolfuyDataSource()
    private val db = Room.databaseBuilder(
            context,
            StationsDatabase::class.java, "stations-database"
            ).build()

    suspend fun fetchTakeoffs(): List<Takeoff> {
        val stations = holfuyDataSource.fetchHolfuyStations()
        val stationsInNor = stations.filter { it.location.countryCode == "NO" }
        val takeoffs: MutableList<Takeoff> = mutableListOf()

        stationsInNor.forEach {
            val name = it.name
            val latLng = it.location.latitude.let { it1 ->
                it.location.longitude.let { it2 ->
                    LatLng(it1,
                        it2
                    )
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

    suspend fun fetchHolfuyStationWeather(id: Int): Wind? {
        val holfObject = holfuyDataSource.fetchHolfuyObject("$id")
        return holfObject.wind
    }
}