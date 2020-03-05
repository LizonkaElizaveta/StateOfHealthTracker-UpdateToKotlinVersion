package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "emotional_test_table")
data class EmotionalTest(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "pathToFile")
    var path: String = "",

    @ColumnInfo(name = "date")
    var date: Long = Calendar.getInstance().timeInMillis
)

