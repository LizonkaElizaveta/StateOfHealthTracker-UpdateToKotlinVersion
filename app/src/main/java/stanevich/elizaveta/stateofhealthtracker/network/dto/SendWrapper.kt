package stanevich.elizaveta.stateofhealthtracker.network.dto

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import stanevich.elizaveta.stateofhealthtracker.App.Companion.context
import stanevich.elizaveta.stateofhealthtracker.home.database.MissClick
import stanevich.elizaveta.stateofhealthtracker.home.database.Rotation
import stanevich.elizaveta.stateofhealthtracker.home.database.Speed
import stanevich.elizaveta.stateofhealthtracker.home.database.States
import stanevich.elizaveta.stateofhealthtracker.profile.database.ProfileDatabase
import stanevich.elizaveta.stateofhealthtracker.test.games.print.model.PrintTest
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.model.TappingTest

data class SendWrapper(
    val userId: Int = getUserId(),
    val timestamp: Long = 0,
    val testType: String? = null,
    val data: DataWrapper = DataWrapper()
)

fun getUserId(): Int = CoroutineScope(Dispatchers.IO).run {
    return if (context != null) {
        ProfileDatabase.getInstance(context!!).profileDatabaseDao.getLastUser()?.phone?.toInt()
            ?: 0
    } else 0
}

fun convertToSendWrapper(dto: Any): SendWrapper {
    val data: DataWrapper
    val timestamp: Long
    var testType: String? = null
    when (dto) {
        is TappingTest -> {
            data = TappingTestDto(
                leftCount = dto.leftCount,
                rightCount = dto.rightCount
            )
            timestamp = dto.date
            testType = "tapping"
        }
        is Speed -> {
            data = MotionDto(speed = dto.speed)
            timestamp = dto.timestamp
        }
        is PrintTest -> {
            data = PrintTestDto(
                erased = dto.erased,
                originalText = dto.originalText,
                userText = dto.userText,
                time = dto.time
            )
            timestamp = dto.date
        }
        is Rotation -> {
            data = SensorAngleDto(
                pitch = dto.pitch,
                azimuth = dto.azimuth,
                roll = dto.roll
            )
            timestamp = dto.timestamp
        }
        is States -> {
            data = UserDataDto(dto.moodToInt(), dto.dyskinesia, dto.pill)
            timestamp = dto.date
        }
        is MissClick -> {
            data = MissClickDto(dto.count, dto.distance)
            timestamp = dto.timestamp
        }
        else -> {
            throw IllegalArgumentException("Unknown type of object: $dto to convert")
        }
    }

    return SendWrapper(timestamp = timestamp, data = data, testType = testType)
}