package stanevich.elizaveta.stateofhealthtracker.screens.states

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States

class StatesViewModel(
    val database: StatesDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var todayState = MutableLiveData<States?>()

    private val states = database.getAllStates()

    init {
        initializeToday()
    }

    private fun initializeToday() {
        uiScope.launch {
            todayState.value = getTodayFromDatabase()
        }
    }

    private suspend fun getTodayFromDatabase(): States? {
        return withContext(Dispatchers.IO) {
            var state = database.getLastState()
            state
        }

    }

    fun onStartTracking(state: String) {
        uiScope.launch {
            val newState = States(statesMood = state)
            insert(newState)
            todayState.value = getTodayFromDatabase()
        }
    }

    private suspend fun insert(state: States) {
        withContext(Dispatchers.IO) {
            database.insert(state)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldState = todayState.value ?: return@launch

            oldState.statesTime = System.currentTimeMillis()

            update(oldState)
        }
    }

    private suspend fun update(state: States) {
        withContext(Dispatchers.IO){
            database.update(state)
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
            todayState.value = null
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO){
            database.clear()
        }
    }
}