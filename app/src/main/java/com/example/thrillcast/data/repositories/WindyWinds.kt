package com.example.thrillcast.data.repositories

data class WindyWinds(
    val time: Long,
    val speedDir800h: Pair<Double, Double>,
    val speedDir850h: Pair<Double, Double>,
    val speedDir900h: Pair<Double, Double>,
    val speedDir950h: Pair<Double, Double>,
)