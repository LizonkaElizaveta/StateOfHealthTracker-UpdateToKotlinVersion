package stanevich.elizaveta.stateofhealthtracker.screens.notifications

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.NotificationsDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.databases.entity.Notifications

class NotificationsViewModel(
    private val database: NotificationsDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var tonightNotification = MutableLiveData<Notifications?>()


    val notifications = database.getAllNotifications()

    private var _showNotEvent = MutableLiveData<Boolean>(false)
    val triggerNotEvent: LiveData<Boolean>
        get() = _showNotEvent

    private fun showDialogNot() {
        _showNotEvent.value = !_showNotEvent.value!!
    }



    init {
        initializeNot()
    }

    private fun initializeNot() {
        uiScope.launch {
            tonightNotification.value = getNotFromDatabase()
        }
    }

    private suspend fun getNotFromDatabase(): Notifications? {
        return withContext(Dispatchers.IO) {
            var notification = database.getLastNotification()
            notification
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newNotifications = Notifications()
            insert(newNotifications)
            tonightNotification.value = getNotFromDatabase()
            showDialogNot()
        }
    }

    private suspend fun insert(notifications: Notifications) {
        withContext(Dispatchers.IO) {
            database.insert(notifications)

            Log.d("mLog", tonightNotification.value.toString())
        }
    }

}

