package stanevich.elizaveta.stateofhealthtracker.data.mining.service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import stanevich.elizaveta.stateofhealthtracker.home.database.StatesDatabase
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.DataStoreAPI
import stanevich.elizaveta.stateofhealthtracker.network.dto.*
import stanevich.elizaveta.stateofhealthtracker.test.games.database.TestingDatabase
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.model.TappingTest
import stanevich.elizaveta.stateofhealthtracker.utils.ConnectivityUtil

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
                sendMotionData(dataStoreApi)
                sendPrintTestData(dataStoreApi)
                sendSensorAngleData(dataStoreApi)
                sendTappingTestData(dataStoreApi)
                sendTappingTestData(dataStoreApi)

                Result.Success()
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
            statesDatabase.clear()
        }
    }

    private suspend fun sendMotionData(dataStoreApi: DataStoreAPI) {
        val speedDatabase = StatesDatabase.getInstance(applicationContext).speedDatabaseDao
        val states = speedDatabase.findAll().map { MotionDto.fromSpeed(it) }
        if (states.isNotEmpty()) {
            dataStoreApi.sendMotionData(states)
            speedDatabase.clear()
        }
    }

    private suspend fun sendSensorAngleData(dataStoreApi: DataStoreAPI) {
        val rotationDatabase = StatesDatabase.getInstance(applicationContext).rotationDatabaseDao
        val states = rotationDatabase.findAll().map { SensorAngleDto.fromRotation(it) }
        if (states.isNotEmpty()) {
            dataStoreApi.sendSensorAngleData(states)
            rotationDatabase.clear()
        }
    }

    private suspend fun sendPrintTestData(dataStoreApi: DataStoreAPI) {
        val printTestDatabase = TestingDatabase.getInstance(applicationContext).printTestDatabaseDao
        val states = printTestDatabase.findAll().map { PrintTestDto.fromPrintTest(it) }
        if (states.isNotEmpty()) {
            dataStoreApi.sendPrintTestData(states)
            printTestDatabase.clear()
        }
    }

    private suspend fun sendTappingTestData(dataStoreApi: DataStoreAPI) {
        val tappingTestDatabase =
            TestingDatabase.getInstance(applicationContext).tappingTestDatabaseDao
        val states = tappingTestDatabase.findAll().map { TappingTestDto.fromTappingTest(it) }
        if (states.isNotEmpty()) {
            dataStoreApi.sendTappingTestData(
                listOf(
                    TappingTestDto.fromTappingTest(
                        TappingTest(
                            1,
                            2,
                            3
                        )
                    )
                )
            )
            tappingTestDatabase.clear()
        }
    }

    private suspend fun sendVoiceTestData(dataStoreApi: DataStoreAPI) {
        val tappingTestDatabase =
            TestingDatabase.getInstance(applicationContext).tappingTestDatabaseDao
        val states = tappingTestDatabase.findAll().map { VoiceTestDto.fromVoiceTest() }
        if (states.isNotEmpty()) {
            dataStoreApi.sendTappingTestData(states)
            tappingTestDatabase.clear()
        }
    }
}