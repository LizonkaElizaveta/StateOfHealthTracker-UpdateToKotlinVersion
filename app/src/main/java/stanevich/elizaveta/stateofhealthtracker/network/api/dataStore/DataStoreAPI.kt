package stanevich.elizaveta.stateofhealthtracker.network.api.dataStore

import stanevich.elizaveta.stateofhealthtracker.network.api.BaseDataSource
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.DataStoreService.DataStore.dataStoreService
import stanevich.elizaveta.stateofhealthtracker.network.dto.SendWrapper

class DataStoreAPI : BaseDataSource() {
    suspend fun isAlive() = getResult { dataStoreService.isAlive() }

    suspend fun sendUserData(sendWrapper: List<SendWrapper>) =
        getResult { dataStoreService.sendUserData(sendWrapper) }

}