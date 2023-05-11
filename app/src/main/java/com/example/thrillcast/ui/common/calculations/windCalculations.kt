import com.example.thrillcast.ui.common.WindCondition

fun checkWindConditions(windDirection: Double?, windSpeed: Double?, greenStart: Int, greenStop: Int): WindCondition {
    return if (isDegreeBetween(windDirection ?: 0.0, greenStart, greenStop) && (windSpeed ?: 0.0) in 3.0..5.0) {
        WindCondition.GOOD
    } else if (isDegreeBetween(windDirection ?: 0.0, greenStart, greenStop) && (windSpeed ?: 0.0) in 2.0..6.0) {
        WindCondition.OKAY
    } else {
        WindCondition.BAD
    }
}

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

fun isDirectionValid(direction: Int, greenStart: Int, greenStop: Int): Boolean {
    return if(greenStart > greenStop) {
        (direction in greenStart .. 360 || direction in 0..greenStop)
    } else (direction in greenStart..greenStop)
}