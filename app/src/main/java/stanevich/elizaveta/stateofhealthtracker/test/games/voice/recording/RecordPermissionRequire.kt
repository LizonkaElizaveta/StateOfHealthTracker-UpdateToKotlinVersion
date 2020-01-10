package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
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
                        showDialogOK(this.activity.getString(R.string.dialogText_voice_permission),
                            DialogInterface.OnClickListener { dialog, which ->
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
                        //ask user to go setting permission
                        showDialogOK(this.activity.getString(R.string.dialogText_voice_permission_go_setting),
                            DialogInterface.OnClickListener { dialog, which ->
                                when (which) {
                                    DialogInterface.BUTTON_POSITIVE -> {
                                        //startActivity(Intent(this.activity, RecordPermissionsActivity::class.java))
                                        val intent = Intent(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", activity.packageName, null)
                                        )
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        activity.startActivity(intent)
                                        activity.finish()
                                    }
                                    DialogInterface.BUTTON_NEGATIVE -> activity.finish()
                                }
                            })
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

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton(R.string.btn_ok, okListener)
            .setNegativeButton(R.string.btn_cancel, okListener)
            .create()
            .show()
    }
}