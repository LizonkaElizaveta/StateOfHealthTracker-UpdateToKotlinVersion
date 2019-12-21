package stanevich.elizaveta.stateofhealthtracker.network.dto

data class SensorAngleDto(
    val pitch: Float = 0.0f,
    val azimuth: Float = 0.0f,
    val roll: Float = 0.0f
) : DataWrapper()