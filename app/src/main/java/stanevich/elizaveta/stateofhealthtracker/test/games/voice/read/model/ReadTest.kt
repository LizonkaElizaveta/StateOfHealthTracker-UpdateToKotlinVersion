package stanevich.elizaveta.stateofhealthtracker.test.games.voice.read.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "read_test_table")
data class ReadTest (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "original_text")
    var originalText: String = "",

    @ColumnInfo(name = "pathToFile")
    var path: String = "",

    @ColumnInfo(name = "date")
    var date: Long = Calendar.getInstance().timeInMillis
)
