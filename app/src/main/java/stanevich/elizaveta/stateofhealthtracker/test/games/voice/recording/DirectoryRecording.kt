package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.os.Environment
import stanevich.elizaveta.stateofhealthtracker.App.Companion.context
import stanevich.elizaveta.stateofhealthtracker.R
import java.io.File

class DirectoryRecording {

    private var fullNameDir: String = ""

    fun createRecDirectory(): Boolean {
        if (isExternalStorage()) {
            val file = File(
                context?.externalMediaDirs.toString(),
                R.string.nameMainFolder.toString()
            )

            fullNameDir =
                """${context?.externalMediaDirs}${File.separator}${R.string.nameMainFolder}"""

            return file.mkdirs()
        }
        return false
    }

    private fun isExternalStorage(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }

    fun getFullNameDirectory(): String {
        return fullNameDir
    }
}