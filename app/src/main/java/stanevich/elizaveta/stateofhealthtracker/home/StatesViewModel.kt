package stanevich.elizaveta.stateofhealthtracker.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.home.database.States
import stanevich.elizaveta.stateofhealthtracker.home.database.StatesDatabaseDao
import java.util.*

class StatesViewModel(
    private val database: StatesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var stateOfHealth = MutableLiveData<States?>()
    val updatedStateOfHealth = MutableLiveData<States?>()


    private var _showMedDialogEvent = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately `show()` a dialog and call `doneShowingMedDialog()`.
     */
    val showMedDialogEvent: LiveData<Boolean>
        get() = _showMedDialogEvent


    fun doneShowingMedDialog() {
        _showMedDialogEvent.value = false
    }


    private var _showThanksDialogEvent = MutableLiveData<Boolean>()

    /**
     * If this is true, immediately `show()` a dialog and call `doneShowingThanksDialog()`.
     */

    val showThanksDialogEvent: LiveData<Boolean>
        get() = _showThanksDialogEvent


    fun doneShowingThanksDialog() {
        _showThanksDialogEvent.value = false
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
            _showThanksDialogEvent.value = true
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
            _showMedDialogEvent.value = true
        }
    }

    fun onStartTrackingPill(pill: String) {
        uiScope.launch {
            updatedStateOfHealth.value = getStates()
            updatedStateOfHealth.value!!.statesDiskinezia = pill
            _showMedDialogEvent.value = true
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
