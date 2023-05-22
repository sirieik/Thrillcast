package com.example.thrillcast.ui.common
import androidx.compose.ui.graphics.Color
import com.example.thrillcast.ui.theme.FlightGreen
import com.example.thrillcast.ui.theme.Red60
import com.example.thrillcast.ui.theme.Yellow

/**
 * Enum som representerer vindforholdene for paragliding, hver med en tilknyttet farge.
 *
 * @property color Fargen som er knyttet til vindforholdet, brukes for visuell indikasjon.
 * @value GOOD Representerer gode vindforhold for paragliding, tilknyttet fargen 'FlightGreen'.
 * @value OKAY Representerer greie, men ikke ideelle, vindforhold for paragliding, tilknyttet fargen 'Yellow'.
 * @value BAD Representerer dårlige eller farlige vindforhold for paragliding, tilknyttet fargen 'Red60'.
 */
enum class WindCondition(val color: Color) {
    GOOD(FlightGreen),
    OKAY(Yellow),
    BAD(Red60)
}