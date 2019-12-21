package stanevich.elizaveta.stateofhealthtracker.network.api

import stanevich.elizaveta.stateofhealthtracker.network.api.DataStoreService.DataStoreAPI.dataStoreService

class DataStoreAPI : BaseDataSource() {

    suspend fun isAlive() = getResult { dataStoreService.isAlive() }

}