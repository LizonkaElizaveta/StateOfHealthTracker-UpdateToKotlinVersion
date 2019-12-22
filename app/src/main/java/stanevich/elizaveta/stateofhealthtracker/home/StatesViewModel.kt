package stanevich.elizaveta.stateofhealthtracker.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.home.database.MissClick
import stanevich.elizaveta.stateofhealthtracker.home.database.MissClickDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.home.database.States
import stanevich.elizaveta.stateofhealthtracker.home.database.StatesDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.view.tracking.ViewTracker
import java.util.*

class StatesViewModel(
    private val statesDatabaseDao: StatesDatabaseDao,
    private val missClickDatabaseDao: MissClickDatabaseDao,
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
            statesDatabaseDao.getLastState()
        }
    }

    fun onStartTrackingMood(mood: String) {
        uiScope.launch {
            updatedStateOfHealth.value = getStates()
            updatedStateOfHealth.value!!.mood = mood
            _showThanksDialogEvent.value = true
        }
    }

    private suspend fun getStates(): States {
        var states = withContext(Dispatchers.IO) {
            statesDatabaseDao.findByDate(Date())
        }
        if (states == null) {
            states = States()
        }
        return states
    }

    fun onStartTrackingDyskinesia(dyskinesia: String) {
        uiScope.launch {
            updatedStateOfHealth.value = getStates()
            updatedStateOfHealth.value!!.dyskinesia = Calendar.getInstance().timeInMillis
            _showMedDialogEvent.value = true
        }
    }

    fun onStartTrackingPill(pill: String) {
        uiScope.launch {
            updatedStateOfHealth.value = getStates()
            updatedStateOfHealth.value!!.pill = Calendar.getInstance().timeInMillis
            _showMedDialogEvent.value = true
        }
    }

    fun saveMissClick(
        timestamp: Long,
        clickDistanceFromCenter: Double,
        closestEvents: List<ViewTracker.ClosestTouchEvent>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            missClickDatabaseDao.insert(
                MissClick(
                    null,
                    timestamp,
                    closestEvents.size,
                    clickDistanceFromCenter
                )
            )
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
            statesDatabaseDao.upsert(newState)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
