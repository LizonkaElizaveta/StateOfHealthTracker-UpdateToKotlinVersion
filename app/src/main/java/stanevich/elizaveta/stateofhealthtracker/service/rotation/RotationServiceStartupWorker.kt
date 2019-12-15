package stanevich.elizaveta.stateofhealthtracker.service.rotation

import android.content.Context
import android.content.Intent
import androidx.concurrent.futures.ResolvableFuture
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture


class RotationServiceStartupWorker(appContext: Context, params: WorkerParameters) :
    ListenableWorker(appContext, params) {

    override fun startWork(): ListenableFuture<Result> {
        startMonitoringServiceIntent(applicationContext)

        return ResolvableFuture.create()
    }

    private fun startMonitoringServiceIntent(context: Context) {
        val intent = Intent(context, RotationDetectorService::class.java)
        intent.putExtra(RotationDetectorService.LOCATION_PERMISSIONS_KEY, true)
        context.startService(intent)
    }

}