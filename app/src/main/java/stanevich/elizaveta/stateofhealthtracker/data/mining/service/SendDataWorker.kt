package stanevich.elizaveta.stateofhealthtracker.data.mining.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import stanevich.elizaveta.stateofhealthtracker.home.database.States
import stanevich.elizaveta.stateofhealthtracker.home.database.StatesDatabase
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.DataStoreAPI
import stanevich.elizaveta.stateofhealthtracker.network.dto.UserDataDto
import stanevich.elizaveta.stateofhealthtracker.utils.ConnectivityUtil
import java.util.*

class SendDataWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {

        if (!ConnectivityUtil.isConnected(applicationContext)) {
            Log.e("NetworkError", "No available connection")
            return@coroutineScope Result.failure()
        }

        withContext(Dispatchers.IO) {
            val dataStoreApi = DataStoreAPI()

            try {
                sendUserData(dataStoreApi)

                Result.retry()
            } catch (e: Exception) {
                Log.e("SendingError", e.toString())
                Result.failure()
            }
        }
    }

    private suspend fun sendUserData(dataStoreApi: DataStoreAPI) {
        val statesDatabase = StatesDatabase.getInstance(applicationContext).statesDatabaseDao
        val states = statesDatabase.findAll().map { UserDataDto.fromStates(it) }
        if (states.isNotEmpty()) {
            dataStoreApi.sendUserData(states)
            dataStoreApi.sendUserData(
                listOf(
                    UserDataDto.fromStates(
                        States(
                            0,
                            Date().time,
                            "+",
                            Date().time,
                            Date().time
                        )
                    )
                )
            )
            statesDatabase.clear()
        }
    }
}