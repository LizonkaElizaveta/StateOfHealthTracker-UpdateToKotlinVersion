package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import stanevich.elizaveta.stateofhealthtracker.R


class RecordPermissionsActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_emotion)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkAndRequestPermissions()) {
                finish()
            }
        } else {
            finish()
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        return when {
            itHaveAllPermissions() -> true
            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    recordingPermission,
                    REQUEST_ID_MULTIPLE_PERMISSIONS
                )
                false
            }
        }
    }

    private val recordingPermission = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    private fun itHaveAllPermissions(): Boolean {
        return recordingPermission.all {
            ActivityCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun allGranted(grantResults: IntArray): Boolean {
        return grantResults.all { it == PackageManager.PERMISSION_GRANTED }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                if (allGranted(grantResults)) {
                    finish()
                } else {
                    if (itShouldRequestPermissionRationaleAny()) {
                        showDialogOK("Разрешите доступ к микрофону",
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

                    }
                }
            }
        }
    }

    private fun itShouldRequestPermissionRationaleAny(): Boolean {
        return recordingPermission.any {
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                it
            )
        }
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }
}