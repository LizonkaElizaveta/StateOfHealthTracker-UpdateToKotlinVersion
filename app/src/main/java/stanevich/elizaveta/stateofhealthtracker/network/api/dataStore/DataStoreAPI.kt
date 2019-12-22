package stanevich.elizaveta.stateofhealthtracker.network.api.dataStore

import stanevich.elizaveta.stateofhealthtracker.network.api.BaseDataSource
import stanevich.elizaveta.stateofhealthtracker.network.api.Result
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.DataStoreService.DataStore.dataStoreService
import stanevich.elizaveta.stateofhealthtracker.network.dto.*

class DataStoreAPI : BaseDataSource() {
    suspend fun isAlive() = getResult { dataStoreService.isAlive() }

    suspend fun sendUserData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendUserData(sendWrapper) }

    suspend fun sendMotionData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendMotionData(sendWrapper) }

    suspend fun sendPrintTestData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendPrintTestData(sendWrapper) }

    suspend fun sendSensorAngleData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendSensorAngleData(sendWrapper) }

    suspend fun sendTappingTestData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendTappingTestData(sendWrapper) }

    suspend fun sendVoiceTestData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendVoiceTestData(sendWrapper) }

    suspend fun sendMissClick(sendWrapper: List<SendWrapper>) = {}

    suspend fun sendByDto(sendWrapper: List<SendWrapper>): Result<DataResponse>? =
        when (sendWrapper[0].data) {
            is MissClickDto -> {
                null
            }
            is TappingTestDto -> {
                sendTappingTestData(sendWrapper)
            }
            is PrintTestDto -> {
                sendPrintTestData(sendWrapper)
            }
            is UserDataDto -> {
                sendUserData(sendWrapper)
            }
            is SensorAngleDto -> {
                sendSensorAngleData(sendWrapper)
            }
            is MotionDto -> {
                sendMotionData(sendWrapper)
            }
            else -> {
                null
            }
        }

}