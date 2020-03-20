package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.model

import stanevich.elizaveta.stateofhealthtracker.App.Companion.context
import stanevich.elizaveta.stateofhealthtracker.R
import java.util.*

object NameFile {
    private var date: Long = 0

    private val name = context?.getString(R.string.baseNameForRecordFile)

    fun getName(): String {
        date = Calendar.getInstance().timeInMillis
        return "$name$date"
    }
}