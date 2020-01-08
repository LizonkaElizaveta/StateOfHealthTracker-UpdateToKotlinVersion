//package stanevich.elizaveta.stateofhealthtracker.notification.manager
//
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.database.sqlite.SQLiteDatabase
//import android.os.Build
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//import stanevich.elizaveta.stateofhealthtracker.notification.database.NotificationsDatabase
//import java.util.*
//
//class BootReceiver : BroadcastReceiver() {
//
//    override fun onReceive(context: Context?, intent: Intent?) {
//        if (Intent.ACTION_BOOT_COMPLETED == intent!!.action) {
//            CoroutineScope(Dispatchers.IO + Job()).launch {
//                val notificationsDatabase = NotificationsDatabase.getInstance(
//                    context ?: throw IllegalStateException("No context provided")
//                ).notificationsDatabaseDao
//                notificationsDatabase.getAllNotifications()
//            }
//
//            val calendar = Calendar.getInstance()
//            initAlarmsFromNotificationDb(context, mDatabase, calendar)
//        }
//    }
//
//    fun initAlarmsFromNotificationDb(
//        context: Context?,
//        mDatabase: SQLiteDatabase,
//        calendar: Calendar
//    ) {
//        val cursor = mDatabase.query(
//            DbSchema.NotificationActivityTable.TABLE_NAME,
//            null, null, null,
//            null, null, null
//        )
//        calendar.timeInMillis = System.currentTimeMillis()
//        if (cursor.moveToFirst()) {
//            do {
//                val time =
//                    cursor.getString(cursor.getColumnIndex(DbSchema.NotificationActivityTable.KEY_TIME))
//                val medication =
//                    cursor.getString(cursor.getColumnIndex(DbSchema.NotificationActivityTable.KEY_NAME_OF_MEDICATION))
//                val id =
//                    cursor.getInt(cursor.getColumnIndex(DbSchema.NotificationActivityTable._ID))
//                calendar[Calendar.HOUR_OF_DAY] = Utils.parseTimeHour(time)
//                calendar[Calendar.MINUTE] = Utils.parseTimeMinute(time)
//                calendar[Calendar.SECOND] = 0
//                initNotificationAlarms(context, calendar.timeInMillis, medication, id)
//            } while (cursor.moveToNext())
//        }
//        cursor.close()
//    }
//
//
//    fun initNotificationAlarms(
//        context: Context?,
//        timeInMillis: Long,
//        medication: String?,
//        id: Int
//    ) {
//        val intent = Intent(context, NotificationReceiver::class.java)
//        intent.putExtra(MEDICATION, medication)
//        intent.putExtra(ID, id)
//        initAlarms(context, timeInMillis, intent, true, id)
//    }
//
//    fun initAlarms(
//        context: Context,
//        timeInMillis: Long,
//        intent: Intent?,
//        isIntervalDay: Boolean,
//        broadcastId: Int
//    ) {
//        val alarmManager =
//            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            broadcastId,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        if (isIntervalDay) {
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                timeInMillis,
//                AlarmManager.INTERVAL_DAY,
//                pendingIntent
//            )
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
//            } else {
//                alarmManager[AlarmManager.RTC_WAKEUP, timeInMillis] = pendingIntent
//            }
//        }
//    }
//
//
//}