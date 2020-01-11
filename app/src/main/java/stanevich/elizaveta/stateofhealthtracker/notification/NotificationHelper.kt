package stanevich.elizaveta.stateofhealthtracker.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.home.StatesFragment
import java.util.*

fun createCategoryNotification(
    notificationId: Int = Random().nextInt(),
    notificationCategory: String?,
    context: Context
) {

    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SmartHealthTracking:wlTag")
    wl.acquire(100000)
    val notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val notificationIntent = Intent(context, StatesFragment::class.java)
    notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

    val pendingIntent = PendingIntent.getActivity(
        context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    val mNotifyBuilder = NotificationCompat.Builder(
        context, notificationCategory.toString() + ""
    ).setSmallIcon(R.drawable.ic_appintro_arrow_back_white)
/*        .setLargeIcon( //doesn't work
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.ic_appintro_arrow_back_white
            )
        )*/
        .setAutoCancel(true).setWhen(System.currentTimeMillis())
        .setContentIntent(pendingIntent)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setContentTitle("Напоминание:")
        .setContentText(notificationCategory)
        .setSound(alarmSound)
        .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            "" + notificationId,
            "Notification$notificationId", importance
        )
        channel.description = notificationCategory
        channel.enableVibration(true)
        channel.setShowBadge(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
        /**    Register the channel with the system; you can't change the importance
        or other notification behaviors after this **/
        notificationManager.createNotificationChannel(channel)
        mNotifyBuilder.setChannelId(channel.id)
    }
    notificationManager.notify(notificationId, mNotifyBuilder.build())
    wl.release()
}

const val NOTIFICATION_WORK_TAG = "notificationWorkTag"