package stanevich.elizaveta.stateofhealthtracker.network.api.dataStore

import stanevich.elizaveta.stateofhealthtracker.network.api.BaseDataSource
import stanevich.elizaveta.stateofhealthtracker.network.api.Result
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.DataStoreService.DataStore.dataStoreService
import stanevich.elizaveta.stateofhealthtracker.network.dto.*

class DataStoreAPI : BaseDataSource() {
    suspend fun isAlive() = getResult { dataStoreService.isAlive() }

    private suspend fun sendUserData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendUserData(sendWrapper) }

    private suspend fun sendMotionData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendMotionData(sendWrapper) }

    private suspend fun sendPrintTestData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendPrintTestData(sendWrapper) }

    private suspend fun sendSensorAngleData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendSensorAngleData(sendWrapper) }

    private suspend fun sendTappingTestData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendTappingTestData(sendWrapper) }

    private suspend fun sendVoiceTestData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendVoiceTestData(sendWrapper) }

    private suspend fun sendReadTestData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendReadTestData(sendWrapper) }

    private suspend fun sendMissClickData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendMissClickData(sendWrapper) }

    suspend fun sendByDto(sendWrapper: List<SendWrapper>): Result<DataResponse>? =
        when (sendWrapper[0].data) {
            is MissClickDto -> {
                sendMissClickData(sendWrapper)
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
            is VoiceTestDto -> {
                sendVoiceTestData(sendWrapper)
            }
            is ReadTestDto -> {
                sendReadTestData(sendWrapper)
            }
            else -> {
                null
            }
        }

}