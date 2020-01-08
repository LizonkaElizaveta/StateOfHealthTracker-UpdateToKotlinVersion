package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import stanevich.elizaveta.stateofhealthtracker.data.mining.service.DataMiningForegroundService

class RecordPermissionsActivity : AppCompatActivity() {

    private lateinit var permissionRequire: RecordPermissionRequire

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionRequire = RecordPermissionRequire(this,
            {
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
}