package com.example.thrillcast.data.repositories
import com.example.thrillcast.data.datamodels.MetObject
import com.example.thrillcast.data.datamodels.WeatherForecast
import com.example.thrillcast.data.datasources.MetDataSource

import javax.inject.Inject
import javax.inject.Singleton

/**
 * En singleton repository-klasse som er ansvarlig for kommunikasjon mellom MetDataSource og ViewModels.
 * Den sorterer også ut den dataen vi trenger.
 *
 * @property metDataSource Et MetDataSource-objekt injisert i konstruktøren.
 * Dette objektet brukes for å hente data fra MetDataSource.
 *
 * @constructor Initialiserer en MetRepository instans med en injisert MetDataSource.
 */
@Singleton
class MetRepository @Inject constructor(private val metDataSource: MetDataSource){

    /**
     * Henter all værdata for en gitt lokasjon for så langt frem i tid som data er tilgjengelig.
     *
     * @param lat Breddegrad for stedet å hente værdata for.
     * @param lon Lengdegrad for stedet å hente værdata for.
     * @return Liste av WeatherForecast objekter, eller null hvis ingen data er tilgjengelig.
     */
    suspend fun fetchLocationForecast(lat:Double, lon:Double): List<WeatherForecast>? {
        val metObject = metDataSource.fetchLocationForecastObject(lat, lon)
        return metObject?.properties?.timeseries
    }

    /**
     * Henter nåværende værdata for gitt lokasjon.
     *
     * @param lat Breddegrad for stedet å hente nåværende værdata for.
     * @param lon Lengdegrad for stedet å hente nåværende værdata for.
     * @return Et MetObject som representerer nåværende værdata, eller null hvis ingen data er tilgjengelig.
     */
    suspend fun fetchNowCastObject(lat: Double, lon: Double): MetObject? {
        return metDataSource.fetchNowCastObject(lat, lon)
    }

}