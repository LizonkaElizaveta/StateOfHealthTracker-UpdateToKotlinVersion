package stanevich.elizaveta.stateofhealthtracker.network.api.dataStore

import stanevich.elizaveta.stateofhealthtracker.network.api.BaseDataSource
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.DataStoreService.DataStoreAPI.dataStoreService

class DataStoreAPI : BaseDataSource() {

    suspend fun isAlive() = getResult { dataStoreService.isAlive() }

}