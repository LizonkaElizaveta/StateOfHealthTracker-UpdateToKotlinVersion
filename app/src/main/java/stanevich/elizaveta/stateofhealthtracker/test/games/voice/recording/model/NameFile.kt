package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import stanevich.elizaveta.stateofhealthtracker.R
import java.util.*

@Entity
object NameFile {
    @ColumnInfo(name = "date")
    var date: Long = Calendar.getInstance().timeInMillis

    @ColumnInfo(name = "name_file")
    private var name: String = R.string.baseNameForRecordFile.toString()

    fun getName(): String {
        return "$name$date"
    }
}