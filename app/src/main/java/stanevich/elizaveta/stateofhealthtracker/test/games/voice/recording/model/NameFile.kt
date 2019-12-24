package stanevich.elizaveta.stateofhealthtracker.test.games.voice.recording.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NameFile{
    constructor(name: String?) {
        this.name = name
    }

    @PrimaryKey(autoGenerate = true)
    private var id : Long? = null

    @ColumnInfo(name = "name_file")
    private var name : String? = null

    fun getName(): String {
        return "$name$id"
    }
}