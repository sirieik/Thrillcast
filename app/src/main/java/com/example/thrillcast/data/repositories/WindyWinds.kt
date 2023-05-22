package com.example.thrillcast.data.repositories

/**
 * Dataklasse som representerer Windy-vindinformasjon(vindhastighet og -retning) for ulike høyder.
 * Laget for å sortere data fra Windy-APIet
 *
 * @param time Tidspunktet for vindinformasjonen.
 * @param speedDir800h Vindhastighet og -retning ved 800 hPa høyde, representert som et par.
 * @param speedDir850h Vindhastighet og -retning ved 850 hPa høyde, representert som et par.
 * @param speedDir900h Vindhastighet og -retning ved 900 hPa høyde, representert som et par.
 * @param speedDir950h Vindhastighet og -retning ved 950 hPa høyde, representert som et par.
 */
data class WindyWinds(
    val time: Long,
    val speedDir800h: Pair<Double, Double>,
    val speedDir850h: Pair<Double, Double>,
    val speedDir900h: Pair<Double, Double>,
    val speedDir950h: Pair<Double, Double>,
) {

    /**
     * Returnerer vindretningen for den angitte høydeindeksen.
     *
     * @param heightIndex Indeksen som representerer høyden (0: 800 hPa, 1: 850 hPa, 2: 900 hPa, 3: 950 hPa).
     * @return Vindretningen på angitt høyde, eller null hvis høydeindeksen er ugyldig.
     */
    fun getWindDirectionForHeight(heightIndex: Int): Double? {
        return when (heightIndex) {
            0 -> speedDir800h.second
            1 -> speedDir850h.second
            2 -> speedDir900h.second
            3 -> speedDir950h.second
            else -> null
        }
    }

    /**
     * Returnerer vindhastigheten for den angitte høydeindeksen.
     *
     * @param heightIndex Indeksen som representerer høyden (0: 800 hPa, 1: 850 hPa, 2: 900 hPa, 3: 950 hPa).
     * @return Vindhastigheten på angitt høyde, eller null hvis høydeindeksen er ugyldig.
     */
    fun getWindSpeedForHeight(heightIndex: Int): Double? {
        return when (heightIndex) {
            0 -> speedDir800h.first
            1 -> speedDir850h.first
            2 -> speedDir900h.first
            3 -> speedDir950h.first
            else -> null
        }
    }
}