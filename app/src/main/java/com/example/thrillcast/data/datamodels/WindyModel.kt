package com.example.thrillcast.data.datamodels

import com.google.gson.annotations.SerializedName

//Dette er dataklassene vi trenger for å parse responsen fra
//windyAPIet med vinddata for de høydene vi ønsker
data class WindyObject (

    @SerializedName("ts"          ) var ts         : ArrayList<Long>    = arrayListOf(),
    @SerializedName("units"       ) var units      : Units?            = Units(),
    @SerializedName("wind_u-950h" ) var windU950h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_u-900h" ) var windU900h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_u-850h" ) var windU850h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_u-800h" ) var windU800h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_v-950h" ) var windV950h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_v-900h" ) var windV900h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_v-850h" ) var windV850h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_v-800h" ) var windV800h : ArrayList<Double> = arrayListOf(),
    @SerializedName("warning"     ) var warning    : String?           = null

)

data class Units (

    @SerializedName("wind_u-950h" ) var windU950h : String? = null,
    @SerializedName("wind_u-900h" ) var windU900h : String? = null,
    @SerializedName("wind_u-850h" ) var windU850h : String? = null,
    @SerializedName("wind_u-800h" ) var windU800h : String? = null,
    @SerializedName("wind_v-950h" ) var windV950h : String? = null,
    @SerializedName("wind_v-900h" ) var windV900h : String? = null,
    @SerializedName("wind_v-850h" ) var windV850h : String? = null,
    @SerializedName("wind_v-800h" ) var windV800h : String? = null

)