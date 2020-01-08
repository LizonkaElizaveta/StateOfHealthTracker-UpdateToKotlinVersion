package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class RecordPermissionRequire(
    private val activity: Activity,
    private val onPermissionGranted: () -> Unit = {},
    private val onPermissionDenied: () -> Unit = {}
) {
    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1
    }

    private val recordingPermission = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> if (grantResults.isNotEmpty() && allGranted(grantResults)) {
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
                recordingPermission,
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        }
    }

    private fun itHaveAllPermissions(): Boolean {
        return recordingPermission.all {
            ActivityCompat.checkSelfPermission(
                activity,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}