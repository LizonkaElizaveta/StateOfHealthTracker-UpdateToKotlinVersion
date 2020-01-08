package stanevich.elizaveta.stateofhealthtracker.notification.manager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.home.StatesFragment

class NotificationReceiver : BroadcastReceiver() {
    companion object {
        const val MEDICATION = "medication"
        const val ID = "id"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val num = intent.getIntExtra(ID, 0)
        val pm =
            context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl =
            pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SmartHealthTracking:wlTag")
        wl.acquire(100000)
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationIntent = Intent(context, StatesFragment::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val mNotifyBuilder = NotificationCompat.Builder(
            context, num.toString() + ""
        ).setSmallIcon(R.drawable.ic_appintro_arrow_back_white)
            .setContentTitle("Напоминание:")
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    Resources.getSystem(),
                    R.drawable.ic_appintro_arrow_back_white
                )
            )
            .setAutoCancel(true).setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText(intent.getStringExtra(MEDICATION))
            .setSound(alarmSound)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("" + num, "name", importance)
            channel.description = "desc"
            channel.enableVibration(true)
            channel.setShowBadge(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            /**    Register the channel with the system; you can't change the importance
            or other notification behaviors after this **/
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(num, mNotifyBuilder.build())
        wl.release()
    }
}