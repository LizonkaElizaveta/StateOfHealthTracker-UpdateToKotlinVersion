package stanevich.elizaveta.stateofhealthtracker.data.mining.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import stanevich.elizaveta.stateofhealthtracker.data.mining.service.DataMiningForegroundService

class LocationPermissionsActivity : AppCompatActivity() {

    private lateinit var permissionRequire: LocationPermissionRequire

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionRequire = LocationPermissionRequire(this,
            {
                startMonitoringServiceIntent(this)
                finish()
            }, {
                finish()
            })
        permissionRequire.requestPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionRequire.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun startMonitoringServiceIntent(context: Context) {
        val intent = Intent(context, DataMiningForegroundService::class.java)
        val withLocation = permissionRequire.itHaveAllPermissions()
        intent.putExtra(DataMiningForegroundService.LOCATION_PERMISSIONS_KEY, withLocation)
        context.startService(intent)
    }

}