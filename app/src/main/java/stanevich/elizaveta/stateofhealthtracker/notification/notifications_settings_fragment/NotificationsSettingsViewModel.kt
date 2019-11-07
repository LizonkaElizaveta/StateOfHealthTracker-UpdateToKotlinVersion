package stanevich.elizaveta.stateofhealthtracker.notification.notifications_settings_fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.notification.adapter.OnStartTracking
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabaseDao

class NotificationsSettingsViewModel(
    private val database: NotificationsDatabaseDao,
    application: Application
) : AndroidViewModel(application),
    OnStartTracking {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var tonightNotification = MutableLiveData<Notifications?>()
    var updatedNotification = MutableLiveData<Notifications?>()


    val notifications = database.getAllNotifications()


    private var _showNotDialogEvent = MutableLiveData<Boolean>(false)

    val showNotDialogEvent: LiveData<Boolean>
        get() = _showNotDialogEvent

    fun doneShowingNotDialog() {
        _showNotDialogEvent.value = false
    }


    init {
        initializeNotification()
    }

    private fun initializeNotification() {
        uiScope.launch {
            tonightNotification.value =
                Notifications()

        }
    }

    private suspend fun getNotFromDatabase(): Notifications? {
        return withContext(Dispatchers.IO) {
            database.getLastNotification()
        }
    }

    override fun startTracking() {
        uiScope.launch {
            // val newNotifications = Notifications()
            insert(tonightNotification.value!!)
            tonightNotification.value =
                Notifications()
        }
    }

    private suspend fun insert(notification: Notifications) {
        withContext(Dispatchers.IO) {
            database.insert(notification)

            Log.d("mLog", "From ViewModel " + notification.toString())
        }
    }

    fun showDialog() {
        _showNotDialogEvent.value = true
    }
}
