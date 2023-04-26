import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.datasources.HolfuyDataSource
import com.google.android.gms.maps.model.LatLng

class HolfuyRepository {

    private val holfuyDataSource: HolfuyDataSource = HolfuyDataSource()

    suspend fun fetchTakeoffs(): List<Takeoff> {
        var stations = holfuyDataSource.fetchHolfuyStations()
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
        val holfObject = holfuyDataSource.fetchHolfuyObject("$id")
        return holfObject.wind
    }
}