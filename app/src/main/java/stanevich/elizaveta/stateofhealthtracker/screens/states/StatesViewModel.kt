package stanevich.elizaveta.stateofhealthtracker.screens.states

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import stanevich.elizaveta.stateofhealthtracker.utils.formatStates
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.*


class StatesViewModel(
    private val database: StatesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var stateOfHealth = MutableLiveData<States?>()

    init {
        initializeState()
    }

    private fun initializeState() {
        uiScope.launch {
            stateOfHealth.value = getStatesFromDatabase()
        }
    }

    private suspend fun getStatesFromDatabase(): States? {
        return withContext(Dispatchers.IO) {
            val state = database.getLastState()
            state
        }

    }

    fun onStartTrackingMood(mood: String) {
        uiScope.launch {
            val newState = States(
                statesMood = mood
            )
            insert(newState)
            stateOfHealth.value = getStatesFromDatabase()


        }
    }

    fun onStartTrackingDiskinezia(diskinezia: String) {
        uiScope.launch {
            val newState = States(
                statesDiskinezia = diskinezia
            )
            insert(newState)
            stateOfHealth.value = getStatesFromDatabase()
        }
    }

    fun onStartTrackingPill(pill: String) {
        uiScope.launch {
            val newState = States(
                statesPill = pill
            )
            insert(newState)
            stateOfHealth.value = getStatesFromDatabase()
        }
    }

    private suspend fun insert(newState: States) {
        withContext(Dispatchers.IO) {

            var updatedDate = newState.statesDate.toInstant()
            updatedDate = updatedDate.truncatedTo(ChronoUnit.MINUTES)

            val unroundedMinutes = updatedDate.getLong(ChronoField.INSTANT_SECONDS)
            val mod = unroundedMinutes % (5 * 60)
            updatedDate =
                updatedDate.plus(if (mod < (3 * 60)) -mod else (5 * 60) - mod, ChronoUnit.SECONDS)

            newState.statesDate = Date.from(updatedDate)

            database.insert(newState)
        }
    }

    private suspend fun update(newState: States){
        withContext(Dispatchers.IO) {
            database.update(newState)
        }
    }


//    private fun showData() {
//
//        database.getListState().observeOn(AndroidS)
//        val list = database.getLastState()
//
//            Log.d(
//                "mLog",
//                "ID = ${list!!.statesId}, " +
//                        "date = ${list.statesDate}, " +
//                        "mood = ${list.statesMood}, " +
//                        "pill = ${list.statesPill}, " +
//                        "diskinezia = ${list.statesDiskinezia}"
//            )
//        }

}


//        Log.d("mLog", database.getLastState().toString())


//    private fun getCalendar(): String {
//        var calendar = Calendar.getInstance()
//        calendar.apply {
//            set(Calendar.YEAR, calendar.get(Calendar.YEAR))
//            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
//            set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
//        }
//        var dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
//
//
//
//        return dateFormat.format(calendar.timeInMillis)
//    }

//    fun onStopTracking() {
//        uiScope.launch {
//            val oldState = stateOfHealth.value ?: return@launch
//
//            oldState.statesTime = System.currentTimeMillis()
//
//            update(oldState)
//        }
//    }
//
//    private suspend fun update(state: States) {
//        withContext(Dispatchers.IO) {
//            database.update(state)
//        }
//    }
//
//    fun onClear() {
//        uiScope.launch {
//            clear()
//            stateOfHealth.value = null
//        }
//    }
//
//    private suspend fun clear() {
//        withContext(Dispatchers.IO) {
//            database.clear()
//        }
//    }
