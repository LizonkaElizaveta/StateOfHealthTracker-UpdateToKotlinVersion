package stanevich.elizaveta.stateofhealthtracker.notification.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.notification.model.CheckBoxModel
import stanevich.elizaveta.stateofhealthtracker.notification.model.populateData

class NotificationsSettingsViewModel(
    private val database: NotificationsDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    var tonightNotification = MutableLiveData<Notifications?>()
    val notifications = database.getAllNotifications()

    private val _checkBox = MutableLiveData<List<CheckBoxModel>>()
    val checkBox: LiveData<List<CheckBoxModel>>
        get() = _checkBox

    private var _showNotDialogCategory = MutableLiveData<Boolean>(false)
    val showNotDialogCategory: LiveData<Boolean>
        get() = _showNotDialogCategory

    private var _showNotDialogTime = MutableLiveData<Boolean>(false)
    val showNotDialogTime: LiveData<Boolean>
        get() = _showNotDialogTime

    private var _showNotDialogDate = MutableLiveData<Boolean>(false)
    val showNotDialogDate: LiveData<Boolean>
        get() = _showNotDialogDate

    init {
        initializeNotification()
        val checkBoxDrawable = populateData()
        viewModelScope.launch {
            initialize(checkBoxDrawable)
        }
    }

    private fun initialize(checkboxes: List<CheckBoxModel>) {
        this._checkBox.value = checkboxes
    }

    private fun initializeNotification() {
        uiScope.launch {
            tonightNotification.value =
                Notifications()
        }
    }

    private suspend fun getNotFromDatabase(): Notifications {
        return withContext(Dispatchers.IO) {
            database.getLastNotification() ?: Notifications()
        }
    }

    fun onStartTracking(category: String, date : String, time: String, repeat : BooleanArray) {
        uiScope.launch {
            tonightNotification.value = getNotFromDatabase()
            tonightNotification.value!!.apply {
                notificationsCategory = category
                notificationsDate = date
                notificationsTime = time
                notificationRepeat = repeat
            }
            insert(tonightNotification.value!!)

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

    fun doneShowingNotDialogCategory() {
        _showNotDialogCategory.value = false
    }

    fun doneShowingNotDialogTime() {
        _showNotDialogTime.value = false
    }

    fun doneShowingNotDialogDate() {
        _showNotDialogDate.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}
