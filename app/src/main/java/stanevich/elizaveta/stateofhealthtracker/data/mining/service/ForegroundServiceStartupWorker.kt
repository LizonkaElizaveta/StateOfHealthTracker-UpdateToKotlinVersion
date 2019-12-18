package stanevich.elizaveta.stateofhealthtracker.data.mining.service

import android.content.Context
import android.content.Intent
import androidx.concurrent.futures.ResolvableFuture
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture


class ForegroundServiceStartupWorker(appContext: Context, params: WorkerParameters) :
    ListenableWorker(appContext, params) {

    override fun startWork(): ListenableFuture<Result> {
        startMonitoringServiceIntent(applicationContext)
        return ResolvableFuture.create()
    }

    private fun startMonitoringServiceIntent(context: Context) {
        val intent = Intent(context, DataMiningForegroundService::class.java)
        intent.putExtra(DataMiningForegroundService.LOCATION_PERMISSIONS_KEY, true)
        context.startService(intent)
    }

}