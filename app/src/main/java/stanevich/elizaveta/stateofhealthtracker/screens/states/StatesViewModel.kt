package stanevich.elizaveta.stateofhealthtracker.screens.states

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
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
    val updatedStateOfHealth = MutableLiveData<States?>()

    private var _showDialogThanksEvent = MutableLiveData<Boolean>(false)

    val showDialogThanksEvent: LiveData<Boolean>
        get() = _showDialogThanksEvent

    fun showDialog() {
        _showDialogThanksEvent.value = !_showDialogThanksEvent.value!!
    }

    init {
        initializeState()
    }

    private fun initializeState() {
        uiScope.launch {
            stateOfHealth.value = getStatesFromDatabase()
            updatedStateOfHealth.observeForever {
                Log.d("mLog", "Updated ${updatedStateOfHealth.value}")
                uiScope.launch {
                    updateStates(updatedStateOfHealth.value!!)
                }
            }
        }
    }

    private suspend fun getStatesFromDatabase(): States? {
        return withContext(Dispatchers.IO) {
            database.getLastState()
        }
    }

    fun onStartTrackingMood(mood: String) {
        uiScope.launch {
            updatedStateOfHealth.value = getStates()
            updatedStateOfHealth.value!!.statesMood = mood
            showDialog()
        }
    }

    private suspend fun getStates(): States {
        var states = withContext(Dispatchers.IO) {
            database.findByDate(Date())
        }
        if (states == null) {
            states = States()
        }
        return states
    }

    fun onStartTrackingDiskinezia(diskinezia: String) {
        uiScope.launch {
            val newState = getStates()
            newState.statesDiskinezia = diskinezia
            upsert(newState)
            stateOfHealth.value = getStatesFromDatabase()
        }
    }

    fun onStartTrackingPill(pill: String) {
        uiScope.launch {
            val newState = getStates()
            newState.statesPill = pill
            upsert(newState)
            stateOfHealth.value = getStatesFromDatabase()
        }
    }

    private suspend fun updateStates(
        newState: States
    ) {
        upsert(newState)
        stateOfHealth.value = getStatesFromDatabase()
    }

    private suspend fun upsert(newState: States) {
        withContext(Dispatchers.IO) {
            database.upsert(newState)
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

//
//    private fun getCalendar(): String {
//        var calendar = Calendar.getInstance()
//        calendar.apply {
//            set(Calendar.YEAR, calendar.get(Calendar.YEAR))
//            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
//            set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
//        }
//
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
