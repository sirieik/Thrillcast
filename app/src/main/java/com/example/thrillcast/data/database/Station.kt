package com.example.thrillcast.data.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stations")
data class Station(

    @PrimaryKey (autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "holfuyID")
    val holfuyID: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "moh")
    val moh: Int,

    @ColumnInfo(name = "greenStart")
    val greenStart: Int,

    @ColumnInfo(name = "greenStop")
    val greenStop: Int,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean

)
