package stanevich.elizaveta.stateofhealthtracker.notification.viewModel

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import kotlinx.coroutines.*
import stanevich.elizaveta.stateofhealthtracker.App
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.notification.manager.NotificationReceiver
import stanevich.elizaveta.stateofhealthtracker.notification.manager.NotificationReceiver.Companion.CATEGORY
import stanevich.elizaveta.stateofhealthtracker.notification.manager.NotificationReceiver.Companion.ID
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

    private fun getFormattingDetailDate(date: String, time: String): Long {
        val formatter = SimpleDateFormat("E, dd MMM, yyyy HH:mm", Locale.getDefault())
        val fullDate = formatter.parse("$date $time")
        return fullDate!!.time
    }

    fun onStartTracking(category: String, date: String, time: String, repeat: BooleanArray) {
        uiScope.launch {
            tonightNotification.value = Notifications(
                null,
                time,
                category,
                date,
                repeat,
                getFormattingDetailDate(date, time)
            )

            insert(tonightNotification.value!!)
        }
    }

    private suspend fun insert(notification: Notifications) {
        withContext(Dispatchers.IO) {
            database.insert(notification)

            val lastNotifications = database.getLast()
            withContext(Dispatchers.Main + Job()) {
                tonightNotification.value = lastNotifications
                notificationHandler()
            }
        }

    }

    private fun notificationHandler() {
        val diffFullDate =
            abs(Calendar.getInstance().timeInMillis - tonightNotification.value!!.timestamp)
        val c = Calendar.getInstance()
        c.timeInMillis = tonightNotification.value!!.timestamp
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val repeatDays = tonightNotification.value!!.repeat
        val everyDaysRepeating = repeatDays.all { it }
        val noRepeatingDays = repeatDays.all { !it }
        val data = Data.Builder().putString(
            NotificationWorker.DATA_NOTIFICATION_CATEGORY,
            tonightNotification.value?.category ?: ""
        ).putInt(
            NotificationWorker.DATA_NOTIFICATION_ID,
            tonightNotification.value?.id?.toInt() ?: Random().nextInt()
        ).build()
        when {
            noRepeatingDays -> {
                startSingleNotification(diffFullDate, data)
            }
            everyDaysRepeating -> {
                startEveryDayNotification(diffFullDate, data)
            }
            else -> {
                startMultiplyNotification(hour, minute, repeatDays)
            }
        }

    }

    private fun startMultiplyNotification(hour: Int, minute: Int, repeatDays: BooleanArray) {
        val calendar = Calendar.getInstance()

        repeatDays.forEachIndexed { index, daySet ->
            if (daySet) {
                var dayOfWeek = index + 2
                if (dayOfWeek > 7) {
                    dayOfWeek = 1
                }
                calendar.apply {
                    timeInMillis = Calendar.getInstance().timeInMillis
                    set(Calendar.DAY_OF_WEEK, dayOfWeek)
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                }
                startAlarmManagerWithWorker(calendar.timeInMillis, dayOfWeek)
            }
        }

    }

    private fun startAlarmManagerWithWorker(time: Long, dayOfWeek: Int) {
        val app = getApplication<App>()
        val intent = Intent(app, NotificationReceiver::class.java)

        val id = (tonightNotification.value?.id ?: 0) * 7 + dayOfWeek
        val category = tonightNotification.value?.category ?: ""
        intent.putExtra(ID, id.toInt())
        intent.putExtra(CATEGORY, category)

        val pendingIntent = PendingIntent.getBroadcast(
            app,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val am = app.getSystemService(ALARM_SERVICE) as AlarmManager
        am.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )

    }

    private fun startEveryDayNotification(diff: Long, data: Data) {
        val requestBuilder =
            PeriodicWorkRequest.Builder(
                NotificationWorker::class.java,
                24,
                TimeUnit.HOURS
            ).setInputData(data).setInitialDelay(diff, TimeUnit.MILLISECONDS)

        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
            "workTag" + (tonightNotification.value?.id ?: Random().nextInt()),
            ExistingPeriodicWorkPolicy.REPLACE, requestBuilder.build()
        )
    }

    private fun startSingleNotification(diff: Long, data: Data) {
        val requestBuilder = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInputData(data)
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
