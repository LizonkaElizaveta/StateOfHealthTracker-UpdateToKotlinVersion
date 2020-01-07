package stanevich.elizaveta.stateofhealthtracker.notification.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.notification.manager.NotificationWorker
import stanevich.elizaveta.stateofhealthtracker.notification.model.CheckBoxModel
import stanevich.elizaveta.stateofhealthtracker.notification.model.populateData
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

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

    fun onStartTracking(category: String, date: String, time: String, repeat: BooleanArray) {
        val formatter = SimpleDateFormat("E, dd MMM, yyyy HH:mm", Locale.getDefault())
        val fullDate = formatter.parse("$date $time")
        val milliseconds = fullDate!!.time
        uiScope.launch {
            tonightNotification.value = Notifications()
            tonightNotification.value!!.apply {
                notificationsCategory = category
                notificationsDate = date
                notificationsTime = time
                notificationRepeat = repeat
                timestamp = milliseconds
            }
            insert(tonightNotification.value!!)
        }
    }

    private suspend fun insert(notification: Notifications) {
        withContext(Dispatchers.IO) {
            database.insert(notification)
            Log.d("mLog", "From ViewModel $notification")
        }
        notificationHandler()
    }

    private fun notificationHandler() {
        val diff = abs(Calendar.getInstance().timeInMillis - tonightNotification.value!!.timestamp)
        val repeatDays = tonightNotification.value!!.notificationRepeat
        val everyDaysRepeating = repeatDays.all { it }
        val noRepeatingDays = !repeatDays.all { it }
        when {
            noRepeatingDays -> {
                startSingleNotification(diff)
            }
            everyDaysRepeating -> {
                startEveryDayNotification(diff)
            }
            else -> {
                Toast.makeText(getApplication(),"Multiply",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun startMultiplyNotification(diff:Long) {
        
    }

    private fun startEveryDayNotification(diff: Long) {
        Toast.makeText(getApplication(),"EveryDay",Toast.LENGTH_SHORT).show()
        val requestBuilder =
            PeriodicWorkRequest.Builder(
                NotificationWorker::class.java,
                15,
                TimeUnit.MINUTES
            )
                .setInitialDelay(diff, TimeUnit.MILLISECONDS)

        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
            "workTag",
            ExistingPeriodicWorkPolicy.REPLACE, requestBuilder.build()
        )
    }

    private fun startSingleNotification(diff: Long) {
        Toast.makeText(getApplication(),"Single",Toast.LENGTH_SHORT).show()
        val requestBuilder = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(diff, TimeUnit.MILLISECONDS)
        WorkManager.getInstance(getApplication()).enqueue(requestBuilder.build())
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
