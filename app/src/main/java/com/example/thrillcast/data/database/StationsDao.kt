package com.example.thrillcast.data.database

import androidx.room.*


@Dao
interface StationsDao {
    @Query("SELECT * FROM stations")
    suspend fun getStations(): List<Station>

    @Upsert
    suspend fun upsertStation(vararg station: Station)

}


