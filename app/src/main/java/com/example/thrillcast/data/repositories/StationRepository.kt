
class StationRepository () {

    //Her skal vi sette opp databasen
    /*
    private val db = Room.databaseBuilder(
            context,
            StationsDatabase::class.java, "stations-database"
            ).build()


     */

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

}