package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import stanevich.elizaveta.stateofhealthtracker.R

@Entity
class NameFile {
    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0

    @ColumnInfo(name = "name_file")
    private var name: String = R.string.baseNameForRecordFile.toString()

    fun getName(): String {
        return "$name$id"
    }
}