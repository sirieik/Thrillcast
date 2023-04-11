package com.example.thrillcast.data.holfuy.stations

data class HolfuyStationsList (

    var id             : Int?            = null,
    var name           : String?         = null,
    var location       : Location?       = Location(),
    var directionZones : DirectionZones? = DirectionZones(),
    var type           : String?         = null

)