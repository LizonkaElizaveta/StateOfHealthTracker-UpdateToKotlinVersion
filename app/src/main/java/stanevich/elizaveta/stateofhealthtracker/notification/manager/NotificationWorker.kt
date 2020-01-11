package stanevich.elizaveta.stateofhealthtracker.notification.manager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import stanevich.elizaveta.stateofhealthtracker.notification.createCategoryNotification
import java.util.*

class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        const val DATA_NOTIFICATION_CATEGORY = "notificationCategory"
        const val DATA_NOTIFICATION_ID = "notificationId"
    }

    override fun doWork(): Result {
        val notificationCategory = inputData.getString(DATA_NOTIFICATION_CATEGORY)
        val notificationId = inputData.getInt(DATA_NOTIFICATION_ID, Random().nextInt())

        createCategoryNotification(notificationId, notificationCategory, applicationContext)
        return Result.success()
    }
}
