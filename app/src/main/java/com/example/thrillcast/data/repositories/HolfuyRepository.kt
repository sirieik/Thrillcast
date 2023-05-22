
package com.example.thrillcast.data.repositories

import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.datasources.HolfuyDataSource
import com.example.thrillcast.ui.common.Takeoff
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

/**
 * En singleton repository-klasse som er ansvarlig for kommunikasjon mellom HolfuyDataSource og ViewModels.
 * Den sorterer også ut den dataen vi trenger.
 *
 * @property holfuyDataSource Et HolfuyDataSource-objekt injisert i konstruktøren.
 * Dette objektet brukes for å hente data fra HolfuyDataSource.
 *
 * @constructor Initialiserer en HolfuyRepository instans med en injisert HolfuyDataSource.
 */
@Singleton
class HolfuyRepository @Inject constructor(private val holfuyDataSource: HolfuyDataSource) {


    /**
     * Henter en liste av takeoff-stasjoner fra Holfuy datakilden, filtrerer for bare de som
     * er lokalisert i Norge og har verdier for grønt område i vindhjulet, og gjør dem om til
     * Takeoff-objekter som inneholder den informasjonen vi trenger videre.
     *
     * Hvis et stasjonsfelt er null, vil det bli erstattet med en standardverdi.
     *
     * @return En liste av Takeoff-objekter som representerer stasjonene i Norge.
     * Returnerer en tom liste hvis ingen stasjoner ble hentet, eller alle hentede stasjoner er utenfor Norge.
     */
    suspend fun fetchTakeoffs(): List<Takeoff> {
        val stations = holfuyDataSource.fetchHolfuyStations()

        return stations?.filter {
            it.location?.countryCode == "NO" && !(it.directionZones?.green?.start == 0 && it.directionZones.green.stop == 0)
        }.run {
            this?.map {
                Takeoff(
                    id = it.id ?: 0,
                    name = it.name ?: "IFI",
                    coordinates = LatLng(
                        it.location?.latitude ?: 58.88083,
                        it.location?.longitude ?: 9.01861
                    ),
                    greenStart = it.directionZones?.green?.start ?: 0,
                    greenStop = it.directionZones?.green?.stop ?: 0,
                    moh = it.location?.altitude ?: 0
                )
            } ?: emptyList()
        }
    }

    /**
     * Henter værdata for en stasjon med den spesifiserte IDen fra HolfuyDataSource.
     *
     * @param id IDen til stasjonen som værdata skal hentes for.
     *
     * @return Vinddata for den spesifiserte stasjonen, eller null hvis stasjonen ikke ble funnet
     * eller stasjonen har ingen vinddata.
     */
    suspend fun fetchHolfuyStationWeather(id: Int): Wind? {
        val holfObject = holfuyDataSource.fetchHolfuyObject("$id")
        return holfObject?.wind
    }
}