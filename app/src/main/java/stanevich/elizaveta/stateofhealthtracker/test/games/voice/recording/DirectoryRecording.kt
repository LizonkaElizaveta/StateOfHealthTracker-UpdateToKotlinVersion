package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording

import android.content.Context
import android.os.Environment
import stanevich.elizaveta.stateofhealthtracker.R
import java.io.File

class DirectoryRecording(private val context: Context?) {

    private var fullNameDir: String = "Recording"

    @Suppress("DEPRECATION")
    fun createRecDirectory(): Boolean {
        if (isExternalStorage()) {
            val file = File(
                Environment.getExternalStorageDirectory(),
                context!!.resources.getString(R.string.nameMainFolder)
            )

            fullNameDir = (Environment.getExternalStorageDirectory().toString()
                    + File.separator
                    + context.resources.getString(R.string.nameMainFolder))

            return file.mkdirs()
        }
        return false
    }

    fun isExternalStorage(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    fun getFullNameDirectory():String{
        return fullNameDir
    }
}