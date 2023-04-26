data class StationList(
    val holfuyStationsList: List<HolfuyStations>,
    val stationCnt: Int
)

data class HolfuyStations(
    val directionZones: DirectionZones,
    val id: Int,
    val location: Location,
    val name: String,
    val type: String
)

data class DirectionZones(
    val green: Green,
    val yellow: Yellow
)

data class Location(
    val altitude: Int,
    val countryCode: String,
    val countryName: String,
    val latitude: Double,
    val longitude: Double
)

data class Green(
    val start: Int,
    val stop: Int
)

data class Yellow(
    val start: Int,
    val stop: Int
)