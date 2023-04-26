package com.example.thrillcast.data.repositories

//Dette er en custom dataklasse for å sortere data fra Windy API-et.
data class WindyWinds(
    val time: Long,
    val speedDir800h: Pair<Double, Double>,
    val speedDir850h: Pair<Double, Double>,
    val speedDir900h: Pair<Double, Double>,
    val speedDir950h: Pair<Double, Double>,
)