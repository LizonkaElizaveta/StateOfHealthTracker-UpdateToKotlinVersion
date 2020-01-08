package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.model

import stanevich.elizaveta.stateofhealthtracker.App.Companion.context
import stanevich.elizaveta.stateofhealthtracker.R
import java.util.*

object NameFile {
    val date: Long = Calendar.getInstance().timeInMillis

    private val name = context?.getString(R.string.baseNameForRecordFile)

    fun getName(): String {
        return "$name$date"
    }
}