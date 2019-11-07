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

    val notifications = database.getAllNotifications()


    private var _showNotDialogCategory = MutableLiveData<Boolean>(false)
    private var _showNotDialogTime = MutableLiveData<Boolean>(false)
    private var _showNotDialogDate = MutableLiveData<Boolean>(false)

    val showNotDialogTime: LiveData<Boolean>
        get() = _showNotDialogTime

    val showNotDialogCategory: LiveData<Boolean>
        get() = _showNotDialogCategory

    val showNotDialogDate: LiveData<Boolean>
        get() = _showNotDialogDate


    fun doneShowingNotDialogCategory() {
        _showNotDialogCategory.value = false
    }

    fun doneShowingNotDialogTime() {
        _showNotDialogTime.value = false
    }

    fun doneShowingNotDialogDate() {
        _showNotDialogDate.value = false
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

            Log.d("mLog", "From ViewModel $notification")
        }
    }

    fun showDialogCategory() {
        _showNotDialogCategory.value = true
    }

    fun showDialogTime() {
        _showNotDialogTime.value = true
    }

    fun showDialogDate() {
        _showNotDialogDate.value = true
    }

}
