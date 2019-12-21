package stanevich.elizaveta.stateofhealthtracker.network.api.dataStore

import stanevich.elizaveta.stateofhealthtracker.network.api.BaseDataSource
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.DataStoreService.DataStore.dataStoreService
import stanevich.elizaveta.stateofhealthtracker.network.dto.SendWrapper

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
}