package com.example.thrillcast.ui.common
import androidx.compose.ui.graphics.Color
import com.example.thrillcast.ui.theme.FlightGreen
import com.example.thrillcast.ui.theme.Red60
import com.example.thrillcast.ui.theme.Yellow

enum class WindCondition(val color: Color) {
    GOOD(FlightGreen),
    OKAY(Yellow),
    BAD(Red60)
}