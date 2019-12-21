package stanevich.elizaveta.stateofhealthtracker.network.dto

import stanevich.elizaveta.stateofhealthtracker.home.database.Rotation

data class SensorAngleDto(
    val pitch: Float = 0.0f,
    val azimuth: Float = 0.0f,
    val roll: Float = 0.0f
) : DataWrapper() {
    companion object {
        fun fromRotation(rotation: Rotation): SendWrapper {
            val data = SensorAngleDto(
                pitch = rotation.pitch,
                azimuth = rotation.azimuth,
                roll = rotation.roll
            )
            return SendWrapper(
                userId = 0,
                timestamp = rotation.timestamp,
                data = data
            )
        }
    }
}