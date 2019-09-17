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


    private var _showNotDialogEvent = MutableLiveData<Boolean>(false)

    val showNotDialogEvent: LiveData<Boolean>
        get() = _showNotDialogEvent

    fun doneShowingNotDialog() {
        _showNotDialogEvent.value = false
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

        }
    }

    private suspend fun insert(notification: Notifications) {
        withContext(Dispatchers.IO) {
            database.insert(notification)

            Log.d("mLog", "From ViewModel " + tonightNotification.value.toString())
        }
    }

    private suspend fun updateStates(
        notification: Notifications
    ) {
        upsert(notification)
        tonightNotification.value = getNotFromDatabase()
    }

    private suspend fun upsert(notification: Notifications) {
        withContext(Dispatchers.IO) {
            database.upsert(notification)
        }
    }

    fun showDialog() {
        _showNotDialogEvent.value = true
    }

//    private suspend fun getNotifications(): Notifications {
//        var notification = withContext(Dispatchers.IO) {
//            database.findByDate(Date())
//        }
//        if (notification == null) {
//            notification = States()
//        }
//        return notification
//    }
}

