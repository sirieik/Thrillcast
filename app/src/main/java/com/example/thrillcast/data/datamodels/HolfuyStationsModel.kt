package com.example.thrillcast.data.datamodels
//Dette er dataklassene vi trenger for å parse responsen fra Holfuy-APIet med alle stasjonene deres

/**
 * Representerer en liste over værstasjoner som er hentet fra Holfuy-APIet.
 *
 * @property holfuyStationList En liste over com.example.thrillcast.data.datamodels.HolfuyStations-objekter.
 * @property stationCnt Antall værstasjoner.
 */
data class StationList(
    val holfuyStationList: List<HolfuyStation>?,
    val stationCnt: Int?
)

/**
 * Representerer en værstasjon fra Holfuy.
 *
 * @property directionZones Retningssonene for værstasjonen.
 * @property id ID-en til værstasjonen.
 * @property location Stedinformasjon for værstasjonen.
 * @property name Navnet på værstasjonen.
 * @property type Typen til værstasjonen.
 */
data class HolfuyStation(
    val directionZones: DirectionZones?,
    val id: Int?,
    val location: Location?,
    val name: String?,
    val type: String?
)

/**
 * Representerer retningssonene for en værstasjon.
 *
 * @property green Den grønne retningssonen.
 * @property yellow Den gule retningssonen.
 */
data class DirectionZones(
    val green: Green?,
    val yellow: Yellow?
)

/**
 * Representerer stedinformasjonen for en værstasjon.
 *
 * @property altitude Høyden til værstasjonen.
 * @property countryCode Landkoden til værstasjonen.
 * @property countryName Landnavnet til værstasjonen.
 * @property latitude Breddegraden til værstasjonen.
 * @property longitude Lengdegraden til værstasjonen.
 */
data class Location(
    val altitude: Int?,
    val countryCode: String?,
    val countryName: String?,
    val latitude: Double?,
    val longitude: Double?
)

/**
 * Representerer den grønne retningssonen for en værstasjon.
 *
 * @property start Startverdien for den grønne retningssonen.
 * @property stop Stoppverdien for den grønne retningssonen.
 */
data class Green(
    val start: Int?,
    val stop: Int?
)

/**
 * Representerer den gule retningssonen for en værstasjon.
 *
 * @property start Startverdien for den gule retningssonen.
 * @property stop Stoppverdien for den gule retningssonen.
 */
data class Yellow(
    val start: Int?,
    val stop: Int?
)