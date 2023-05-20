package com.example.thrillcast.ui.common.calculations

import com.example.thrillcast.ui.common.WindCondition

/**
 * Sjekker vindforholdene basert på vindretning og vindhastighet, i forhold til et gitt vinkelområde.
 *
 * @param windDirection Vindretningen, i grader. Kan være null.
 * @param windSpeed Vindhastigheten. Kan være null.
 * @param greenStart Startpunktet for det grønne vinkelområdet, i grader.
 * @param greenStop Stoppunktet for det grønne vinkelområdet, i grader.
 * @return En [WindCondition]-verdi som representerer vindforholdene.
 */
fun checkWindConditions(windDirection: Double?, windSpeed: Double?, greenStart: Int, greenStop: Int): WindCondition {
    return if (isDegreeBetween(windDirection ?: 0.0, greenStart, greenStop) && (windSpeed ?: 0.0) in 3.0..5.0) {
        WindCondition.GOOD
    } else if (isDegreeBetween(windDirection ?: 0.0, greenStart, greenStop) && (windSpeed ?: 0.0) in 2.0..6.0) {
        WindCondition.OKAY
    } else {
        WindCondition.BAD
    }
}

/**
 * Sjekker om en vinkelverdi er innenfor et gitt område.
 *
 * @param value Vinkelverdien som skal sjekkes, i grader.
 * @param min Minimumsgrensen for vinkelområdet, i grader.
 * @param max Maksimumsgrensen for vinkelområdet, i grader.
 * @return `true` hvis `value` er innenfor vinkelområdet, ellers `false`.
 */
fun isDegreeBetween(value: Double, min: Int, max: Int): Boolean {
    val valueRadians = Math.toRadians(value)
    val minRadians = Math.toRadians(min.toDouble())
    val maxRadians = Math.toRadians(max.toDouble())

    return if (minRadians <= maxRadians) {
        valueRadians in minRadians..maxRadians
    } else {
        valueRadians >= minRadians || valueRadians <= maxRadians
    }
}