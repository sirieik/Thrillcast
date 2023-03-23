package com.example.thrillcast.data.windy

import com.google.gson.annotations.SerializedName

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