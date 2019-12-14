package stanevich.elizaveta.stateofhealthtracker.service.rotation

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import stanevich.elizaveta.stateofhealthtracker.R
import stanevich.elizaveta.stateofhealthtracker.service.location.LocationProvider
import java.util.concurrent.TimeUnit


/**
 * Сервис, который постоянно мониторинг углы наклона устройств
 * и записывает их в базу данных.
 * Данные записываются в базу данных каждую секунду,
 * при изменении значений. Если телефон находится в одинаковом положении,
 * т.е его углы наклона не меняются, то данные в базу не сохраняются.
 */
class RotationDetectorService(private val onLocationUpdate: (location: Location) -> Unit = { _ -> }) :
    Service() {

    companion object {
        const val LOCATION_PERMISSIONS_KEY = "LOCATION_PERMISSIONS_KEY"
        private const val NOTIFICATION_ENABLED = "NOTIFICATION_ENABLED "

        const val WORK_NAME_DEFAULT = "Rotation service startup worker"
        const val WORK_DELAY_MINUTES = 30L

        fun saveNotificationEnabled(enabled: Boolean, context: Context) {
            context
                .applicationContext
                .getSharedPreferences(NOTIFICATION_ENABLED, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(NOTIFICATION_ENABLED, enabled)
                .apply()
        }

        fun isNotificationEnabled(context: Context): Boolean {
            return context
                .applicationContext
                .getSharedPreferences(NOTIFICATION_ENABLED, Context.MODE_PRIVATE)
                .getBoolean(NOTIFICATION_ENABLED, true)

        }
    }

    private lateinit var rotationDetector: RotationDetector
    private lateinit var locationProvider: LocationProvider

    override fun onCreate() {
        super.onCreate()
        this.rotationDetector = RotationDetector(this, 100)
        this.locationProvider = LocationProvider(1000, this, onLocationUpdate)
        rotationDetector.getOrientation {}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            moveToForeground()
        }
    }

    private fun createWorkRequest() = PeriodicWorkRequest
        .Builder(
            RotationServiceStartupWorker::class.java,
            WORK_DELAY_MINUTES,
            TimeUnit.MINUTES,
            WORK_DELAY_MINUTES / 2,
            TimeUnit.MINUTES
        )
        // setting a backoff on case the work needs to retry
        .setBackoffCriteria(
            BackoffPolicy.LINEAR,
            PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS
        )
        .build()


    private fun moveToForeground() {
        val disableIntent = Intent(this, RotationDetectorService::class.java)
        disableIntent.putExtra(NOTIFICATION_ENABLED, false)
        val deletePendingIntent = PendingIntent.getService(this, 0, disableIntent, 0)
        val notificationManager = SHTNotificationManager(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createMainNotificationChannel()
            val notification =
                NotificationCompat.Builder(this, notificationManager.getMainNotificationId())
                    .setSmallIcon(R.drawable.ic_target)
                    .setContentTitle("Rotation Title")
                    .setContentText("Rotation Content")
                    .setGroup("Rotation group")
                    .addAction(
                        android.R.drawable.ic_delete,
                        "Disable monitoring action",
                        deletePendingIntent
                    )
                    .build()
            startForeground(1224, notification)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent?.getBooleanExtra(LOCATION_PERMISSIONS_KEY, false) == true) {
            locationProvider.startTrackingLocation()
        }
        val savedState = isNotificationEnabled(this)
        val enabled = intent?.getBooleanExtra(NOTIFICATION_ENABLED, savedState) ?: savedState
        saveNotificationEnabled(enabled, this)
        if (!enabled) {
            stopScheduledWorks()
            stopSelf()
            return START_NOT_STICKY
        }
        scheduleWorks()
        return START_STICKY
    }

    private fun scheduleWorks() {
        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                WORK_NAME_DEFAULT,
                ExistingPeriodicWorkPolicy.KEEP,
                createWorkRequest()
            )
    }

    private fun stopScheduledWorks() {
        WorkManager
            .getInstance(this).cancelUniqueWork(WORK_NAME_DEFAULT)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationProvider.stopTrackingLocation()
        rotationDetector.clear()
        val broadcastIntent = Intent("stanevich.elizaveta.stateofhealthtracker.service.rotation")
        sendBroadcast(broadcastIntent)
    }

}
