package com.example.thrillcast.data.datamodels

//Dette er dataklassene vi trenger for å parse responsen fra holfuyAPIet med værdata for
//stasjonsIDen vi sender med i kallet

/**
 * Representerer et Holfuy-objekt med værdata som er hentet for en gitt stasjon fra Holfuy-APIet.
 *
 * @property dateTime Dato og tid for værdataene.
 * @property light Lysintensiteten i værdataene.
 * @property stationId ID-en til værstasjonen.
 * @property stationName Navnet på værstasjonen.
 * @property temperature Temperaturen i værdataene.
 * @property wind Vindinformasjonen i værdataene.
 */
data class HolfuyObject(
    val dateTime: String?,
    val light: Int?,
    val stationId: Int?,
    val stationName: String?,
    val temperature: Double?,
    val wind: Wind?
)

/**
 * Representerer vindinformasjonen i et Holfuy-objekt for værdata.
 *
 * @property direction Vindretningen.
 * @property gust Vindhastigheten for vindkast.
 * @property min Minimum vindhastighet.
 * @property speed Vindhastigheten.
 * @property unit Enheten for vindhastigheten.
 */
data class Wind(
    val direction: Int?,
    val gust: Double?,
    val min: Double?,
    val speed: Double?,
    val unit: String?
)