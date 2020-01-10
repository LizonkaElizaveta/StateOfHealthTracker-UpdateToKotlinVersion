package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import stanevich.elizaveta.stateofhealthtracker.dialogs.DataVoiceDialogPermission
import stanevich.elizaveta.stateofhealthtracker.dialogs.DataVoiceDialogPermissionSetting

class RecordPermissionRequire(
    private val activity: Activity,
    private val supportFragmentManager: FragmentManager
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
                        DataVoiceDialogPermission({
                            checkAndRequestPermissions()
                        }, {
                            activity.finish()
                        }).show(supportFragmentManager, "DataVoiceDialogPermission")
                    } else {
                        //ask user to go setting permission
                        DataVoiceDialogPermissionSetting({
                            startSettingAp(activity)
                            activity.finish()
                        }, {
                            activity.finish()
                        }).show(supportFragmentManager, "DataVoiceDialogPermissionSetting")
                    }
                }
            }
        }
    }

    private fun startSettingAp(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
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
}