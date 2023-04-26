package com.example.thrillcast.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Station::class], version = 1)
abstract class StationsDatabase: RoomDatabase() {
    abstract fun stationsDao(): StationsDao

}


