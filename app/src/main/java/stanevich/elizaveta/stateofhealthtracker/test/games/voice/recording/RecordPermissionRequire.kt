package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import stanevich.elizaveta.stateofhealthtracker.R

class RecordPermissionRequire(
    private val activity: Activity
) {
    companion object {
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    }

    private val recordingPermission = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && allGranted(grantResults)) {
                    activity.finish()
                } else {
                    if (itShouldRequestPermissionRationaleAny()) {
                        showDialogOK(DialogInterface.OnClickListener { dialog, which ->
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                DialogInterface.BUTTON_NEGATIVE -> onRequestPermissionsResult(
                                    requestCode,
                                    permissions,
                                    grantResults
                                )
                            }
                        })
                    } else {

                    }
                }
            }
        }
    }

    private fun allGranted(grantResults: IntArray): Boolean {
        return grantResults.all { it == PackageManager.PERMISSION_GRANTED }
    }

    fun checkAndRequestPermissions(): Boolean {
        return when {
            checkAllPermissions() -> true
            else -> {
                ActivityCompat.requestPermissions(
                    activity,
                    recordingPermission,
                    REQUEST_ID_MULTIPLE_PERMISSIONS
                )
                false
            }
        }
    }

    fun checkAllPermissions(): Boolean {
        return recordingPermission.all {
            ActivityCompat.checkSelfPermission(
                activity,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun itShouldRequestPermissionRationaleAny(): Boolean {
        return recordingPermission.any {
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                it
            )
        }
    }

    private fun showDialogOK(okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(activity)
            .setMessage(R.string.dialogText_voice_permission)
            .setPositiveButton(R.string.btn_ok, okListener)
            .setNegativeButton(R.string.btn_cancel, okListener)
            .create()
            .show()
    }
}