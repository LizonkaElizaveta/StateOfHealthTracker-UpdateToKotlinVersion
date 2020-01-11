package stanevich.elizaveta.stateofhealthtracker.notification.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import stanevich.elizaveta.stateofhealthtracker.notification.database.Notifications
import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabase
import stanevich.elizaveta.stateofhealthtracker.notification.manager.NotificationReceiver.Companion.CATEGORY
import stanevich.elizaveta.stateofhealthtracker.notification.manager.NotificationReceiver.Companion.ID
import java.util.*

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val notifications: List<Notifications> = runBlocking {
                return@runBlocking withContext(Dispatchers.IO + Job()) {
                    return@withContext NotificationsDatabase.getInstance(context)
                        .notificationsDatabaseDao.getAllNotifications()
                }
            }

            val calendar = Calendar.getInstance()
            initAlarmsFromNotificationDb(
                context,
                notifications,
                calendar
            )
        }
    }

    private fun initAlarmsFromNotificationDb(
        context: Context,
        database: List<Notifications>,
        calendar: Calendar
    ) {
        Log.d("BoorReceiver", "initAlarmsFromNotificationDb")
        database.forEach { notification ->
            val repeatDays = notification.repeat
            val everyDaysRepeating = repeatDays.all { it }
            val noRepeatingDays = repeatDays.all { !it }
            if (everyDaysRepeating || noRepeatingDays) {
                return
            } else {
                val c = Calendar.getInstance()
                c.timeInMillis = notification.timestamp
                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)
                val category = notification.category
                repeatDays.forEachIndexed { index, daySet ->
                    if (daySet) {
                        var dayOfWeek = index + 2
                        if (dayOfWeek > 7) {
                            dayOfWeek = 1
                        }
                        val id = notification.id!! * 7 + dayOfWeek
                        calendar.apply {
                            set(Calendar.DAY_OF_WEEK, dayOfWeek)
                            set(Calendar.HOUR_OF_DAY, hour)
                            set(Calendar.MINUTE, minute)
                            set(Calendar.SECOND, 0)
                        }
                        initNotificationAlarms(context, calendar.timeInMillis, category, id.toInt())
                    }
                }
            }
        }
    }


    private fun initNotificationAlarms(
        context: Context,
        timeInMillis: Long,
        category: String?,
        id: Int
    ) {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(CATEGORY, category)
        intent.putExtra(ID, id)
        initAlarms(context, timeInMillis, intent, id)
    }


    private fun initAlarms(
        context: Context,
        timeInMillis: Long,
        intent: Intent?,
        broadcastId: Int
    ) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            broadcastId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )
    }
}