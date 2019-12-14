package stanevich.elizaveta.stateofhealthtracker.service.rotation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class SHTNotificationManager(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "Rotation"
        private const val CHANNEL_NAME = "Rotation service chanel"
        private const val CHANNEL_DESCRIPTION = "channel description"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMainNotificationId(): String {
        return CHANNEL_ID
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createMainNotificationChannel() {
        val id = CHANNEL_ID
        val name = CHANNEL_NAME
        val description = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel = NotificationChannel(id, name, importance)
        mChannel.description = description
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val existingChanel = notificationManager.getNotificationChannel(id)
        if (existingChanel != null) {
            notificationManager.deleteNotificationChannel(id)
        }
        notificationManager.createNotificationChannel(mChannel)
    }
}