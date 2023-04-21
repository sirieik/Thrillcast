package com.example.thrillcast.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface StationsDao {
    @Query("SELECT * FROM stations")
    suspend fun getStations(): List<Station>

    @Insert
    suspend fun insertStation(vararg station: Station)

    @Update
    suspend fun changeFavorite(vararg station: Station)

}


