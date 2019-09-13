package stanevich.elizaveta.stateofhealthtracker.screens.states

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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

    private var _showThEvent = MutableLiveData<Boolean>(false)
    private var _showMedEvent = MutableLiveData<Boolean>(false)

    val triggerThanksEvent: LiveData<Boolean>
        get() = _showThEvent

    val triggerMedicationEvent: LiveData<Boolean>
        get() = _showMedEvent

    private fun showDialogThanks() {
        _showThEvent.value = !_showThEvent.value!!
    }

    private fun showDialogMed() {
        _showMedEvent.value = !_showMedEvent.value!!
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
            showDialogThanks()
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
            updatedStateOfHealth.value = getStates()
            updatedStateOfHealth.value!!.statesDiskinezia = diskinezia
            showDialogMed()
        }
    }

    fun onStartTrackingPill(pill: String) {
        uiScope.launch {
            updatedStateOfHealth.value = getStates()
            updatedStateOfHealth.value!!.statesDiskinezia = pill
            showDialogMed()
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

}

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
