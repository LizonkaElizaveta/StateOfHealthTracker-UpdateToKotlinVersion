package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.os.Environment
import stanevich.elizaveta.stateofhealthtracker.App.Companion.context
import stanevich.elizaveta.stateofhealthtracker.R
import java.io.File

class DirectoryRecording {

    val fullNameDir =
        "${context?.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)}${File.separator}${context?.getString(
            R.string.nameMainFolder
        )}"

    fun createRecDirectory(): Boolean {
        if (!isExternalStorage()) return false

        val directoryRecording = File(fullNameDir)

        if (directoryRecording.exists()) return true

        return directoryRecording.mkdirs()
    }

    private fun isExternalStorage(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }
}