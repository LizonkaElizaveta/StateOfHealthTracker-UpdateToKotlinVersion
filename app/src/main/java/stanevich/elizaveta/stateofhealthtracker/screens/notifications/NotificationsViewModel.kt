package stanevich.elizaveta.stateofhealthtracker.screens.notifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

    private var tonightNotification = MutableLiveData<Notifications?>()


    val notifications = database.getAllNotifications()


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

    private suspend fun insert(notifications: Notifications) {
        withContext(Dispatchers.IO) {
            database.insert(notifications)
        }
    }

}

