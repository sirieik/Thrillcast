
package com.example.thrillcast.data.repositories

import android.util.Log
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.datasources.HolfuyDataSource
import com.example.thrillcast.ui.viewmodels.map.Takeoff
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

/**
 * En singleton repository-klasse som er ansvarlig for kommunikasjon med HolfuyDataSource.
 *
 * @property holfuyDataSource Et HolfuyDataSource-objekt injisert i konstruktøren.
 * Dette objektet brukes for å hente data fra Holfuy datakilden.
 *
 * @constructor Oppretter en instans av HolfuyRepository.
 */
@Singleton
class HolfuyRepository @Inject constructor(private val holfuyDataSource: HolfuyDataSource) {

    /**
     * Henter en liste av takeoff-stasjoner fra Holfuy datakilden, filtrerer for bare de som
     * er lokalisert i Norge, og gjør dem om til Takeoff-objekter med kun den informasjonen vi trenger.
     *
     * Hvis et stasjonsfelt er null, vil det bli erstattet med en standardverdi.
     *
     * @return En liste av Takeoff-objekter som representerer stasjonene i Norge.
     * Returnerer en tom liste hvis ingen stasjoner ble hentet, eller alle hentede stasjoner er utenfor Norge.
     */
    suspend fun fetchTakeoffs(): List<Takeoff> {
        val stations = holfuyDataSource.fetchHolfuyStations()

        /*
        return stations?.filter { it.location?.countryCode == "NO" }.run {
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

         */

        //Vi sorterer ut kun de takeofflokasjonene som er i Norge
        val stationsInNor = stations?.filter { it.location?.countryCode == "NO" }

        val takeoffs: MutableList<Takeoff> = mutableListOf()

        //Her oppretter vi Takeoff-objekter for hver lokasjon i listen med kun norske stasjoner
        //og legger de til i listen over.
        stationsInNor?.forEach {
            val name = it.name ?: "IFI"
            val latLng = LatLng(
                it.location?.latitude ?: 58.88083,
                it.location?.longitude ?: 9.01861
            )

            val id = it.id ?: 0
            val greenStart = it.directionZones?.green?.start ?: 0
            val greenStop = it.directionZones?.green?.stop ?: 0
            val moh = it.location?.altitude ?: 0
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

/*
        //Vi sorterer ut kun de takeofflokasjonene som er i Norge
        val stationsInNor = stations?.filter { it.location?.countryCode == "NO" }

        val takeoffs: MutableList<Takeoff> = mutableListOf()


        //Her oppretter vi Takeoff-objekter for hver lokasjon i listen med kun norske stasjoner
        //og legger de til i listen over.
        stationsInNor?.forEach {
            val name = it.name ?: "IFI"
            val latLng = LatLng(
                it.location?.latitude ?: 58.88083,
                it.location?.longitude ?: 9.01861
            )

            val id = it.id ?: 0
            val greenStart = it.directionZones?.green?.start ?: 0
            val greenStop = it.directionZones?.green?.stop ?: 0
            val moh = it.location?.altitude ?: 0
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

         */