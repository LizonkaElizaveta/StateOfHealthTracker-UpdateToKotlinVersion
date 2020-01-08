package stanevich.elizaveta.stateofhealthtracker.notification.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import stanevich.elizaveta.stateofhealthtracker.R

class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channelId = "WorkManager_00"

    companion object {
        const val WORK_TAG = "notificationWork"
    }

    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }


    private fun sendNotification() {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Single Worker")
            .setContentText("This notification is from Single Worker!!")
            .setSmallIcon(R.drawable.ic_appintro_arrow_back_white)

        notificationManager.notify(1, notification.build())
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "WorkManager",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            return notificationManager.createNotificationChannel(channel)
        }
    }
}
