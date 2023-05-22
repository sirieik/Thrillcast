package com.example.thrillcast.data.datamodels

import com.google.gson.annotations.SerializedName

//Dette er dataklassene vi trenger for å parse responsen fra
//windyAPIet med vinddata for de høydene vi ønsker

/**
 * Representerer et WindyObject med vinddata hentet fra Windy-APIet for en gitt lokasjon.
 *
 * Vindhastighet og -retning defineres av en todimensjonal vektor. Komponenten u definerer
 * hastigheten til en vind som blåser fra vest mot øst (en negativ verdi indikerer
 * derfor motsatt retning). Komponenten v definerer tilsvarende hastighet til en
 * vind som blåser fra sør mot nord.
 *
 * @property ts En liste med timestamps knyttet til vinddataene.
 * @property units Enhetene for måling av vinddataene (f.eks. "m/s").
 * @property windU950h Vinddata for u-komponenten ved 950hPa.
 * @property windU900h Vinddata for u-komponenten ved 900hPa.
 * @property windU850h Vinddata for u-komponenten ved 850hPa.
 * @property windU800h Vinddata for u-komponenten ved 800hPa.
 * @property windV950h Vinddata for v-komponenten ved 950hPa.
 * @property windV900h Vinddata for v-komponenten ved 900hPa.
 * @property windV850h Vinddata for v-komponenten ved 850hPa.
 * @property windV800h Vinddata for v-komponenten ved 800hPa.
 * @property warning Eventuelle advarsler knyttet til dataene.
 */
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

/**
 * Representerer enhetene for måling av vinddataene (f.eks. "m/s").
 *
 * @property windU950h Enheten for måling av u-komponenten ved 950hPa.
 * @property windU900h Enheten for måling av u-komponenten ved 900hPa.
 * @property windU850h Enheten for måling av u-komponenten ved 850hPa.
 * @property windU800h Enheten for måling av u-komponenten ved 800hPa.
 * @property windV950h Enheten for måling av v-komponenten ved 950hPa.
 * @property windV900h Enheten for måling av v-komponenten ved 900hPa.
 * @property windV850h Enheten for måling av v-komponenten ved 850hPa.
 * @property windV800h Enheten for måling av v-komponenten ved 800hPa.
 */
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