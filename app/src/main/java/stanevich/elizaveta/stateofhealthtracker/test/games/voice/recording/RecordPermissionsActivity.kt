package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import stanevich.elizaveta.stateofhealthtracker.R


class RecordPermissionsActivity : AppCompatActivity() {

    private lateinit var permissionRequire: RecordPermissionRequire

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.action_voice_permission)

        permissionRequire = RecordPermissionRequire(this, supportFragmentManager)

        if (permissionRequire.checkAndRequestPermissions()) {
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionRequire.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}