package stanevich.elizaveta.stateofhealthtracker.screens.states

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import java.text.SimpleDateFormat
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

    private var todayState = MutableLiveData<States?>()

    init {
        initializeState()
    }

    private fun initializeState() {
        uiScope.launch {
            todayState.value = getStatesFromDatabase()
        }
    }

    private suspend fun getStatesFromDatabase(): States? {
        return withContext(Dispatchers.IO) {
            var state = database.getLastState()
            state
        }

    }

    fun onStartTrackingMood(mood: String) {
        uiScope.launch {
            val newState = States(
                statesMood = mood
            )
            insert(newState)
            todayState.value = getStatesFromDatabase()
        }
    }

    fun onStartTrackingDiskinezia(diskinezia: String) {
        uiScope.launch {
            val newState = States(
                statesDiskinezia = diskinezia
            )
            insert(newState)
            todayState.value = getStatesFromDatabase()
        }
    }

    fun onStartTrackingPill(pill: String) {
        uiScope.launch {
            val newState = States(
                statesPill = pill
            )
            insert(newState)
            todayState.value = getStatesFromDatabase()
        }
    }

    private suspend fun insert(
        newState: States
    ) {
        withContext(Dispatchers.IO) {
            database.insert(newState)
        }
    }

    private fun getCalendar(): String {
        var calendar = Calendar.getInstance()
        calendar.apply {
            set(Calendar.YEAR, calendar.get(Calendar.YEAR))
            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
        }
        var dateFormat = SimpleDateFormat("dd.MM.yyyy")


        return dateFormat.format(calendar.timeInMillis)
    }

//    fun onStopTracking() {
//        uiScope.launch {
//            val oldState = todayState.value ?: return@launch
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
//            todayState.value = null
//        }
//    }
//
//    private suspend fun clear() {
//        withContext(Dispatchers.IO) {
//            database.clear()
//        }
//    }
}