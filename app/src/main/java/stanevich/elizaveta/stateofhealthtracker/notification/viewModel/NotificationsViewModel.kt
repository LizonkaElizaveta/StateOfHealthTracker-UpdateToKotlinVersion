package stanevich.elizaveta.stateofhealthtracker.notification.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabaseDao

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

    val notifications: MutableLiveData<List<Notifications>> = MutableLiveData(listOf())

    init {
        initializeNot()
    }

    private fun initializeNot() {
        uiScope.launch {
            tonightNotification.value = Notifications()
            withContext(Dispatchers.IO) {
                notifications.postValue(database.getAllNotifications())
            }
        }
    }

    fun delete(notId: Long) {
        CoroutineScope(Dispatchers.IO + viewModelJob).launch {
            database.deleteByNotificationId(notId)
            notifications.postValue(database.getAllNotifications())
        }
    }
}

