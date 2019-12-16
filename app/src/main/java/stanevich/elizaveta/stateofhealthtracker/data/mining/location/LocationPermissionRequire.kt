package stanevich.elizaveta.stateofhealthtracker.data.mining.location

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class LocationPermissionRequire(
    private val activity: Activity,
    private val onPermissionGranted: () -> Unit = {},
    private val onPermissionDenied: () -> Unit = {}
) {
    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 0x70CA
    }

    private val locationPermission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> if (grantResults.isNotEmpty() && allGranted(grantResults)) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }

    private fun allGranted(grantResults: IntArray): Boolean {
        return grantResults.all { it == PackageManager.PERMISSION_GRANTED }
    }

    fun requestPermissions() {
        if (itHaveAllPermissions()) {
            onPermissionGranted()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                locationPermission,
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    fun itHaveAllPermissions(): Boolean {
        return locationPermission.all {
            ActivityCompat.checkSelfPermission(
                activity,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}