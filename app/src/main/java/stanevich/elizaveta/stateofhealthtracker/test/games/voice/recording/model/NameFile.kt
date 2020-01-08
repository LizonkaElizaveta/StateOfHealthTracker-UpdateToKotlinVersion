package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.model

import androidx.room.ColumnInfo
import stanevich.elizaveta.stateofhealthtracker.R
import java.util.*

object NameFile {
    @ColumnInfo(name = "date")
    var date: Long = Calendar.getInstance().timeInMillis

    @ColumnInfo(name = "name_file")
    private var name: String = R.string.baseNameForRecordFile.toString()

    fun getName(): String {
        return "$name$date"
    }
}