package stanevich.elizaveta.stateofhealthtracker.network.dto

import stanevich.elizaveta.stateofhealthtracker.home.database.Speed

data class MotionDto(
    val speed: Float
) : DataWrapper() {
    companion object {
        fun fromSpeed(speed: Speed): SendWrapper {
            val data = MotionDto(speed = speed.speed)
            return SendWrapper(timestamp = speed.timestamp, data = data)
        }
    }
}