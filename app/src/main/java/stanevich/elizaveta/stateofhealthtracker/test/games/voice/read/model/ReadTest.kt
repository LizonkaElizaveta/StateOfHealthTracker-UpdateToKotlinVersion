package stanevich.elizaveta.stateofhealthtracker.test.games.voice.read.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "read_test_table")
data class ReadTest(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "original_text")
    var originalText: String = "",

    @ColumnInfo(name = "pathToFile")
    var path: String = "",

    @ColumnInfo(name = "state")
    var emotion: String = "",

    @ColumnInfo(name = "date")
    var date: Long = Calendar.getInstance().timeInMillis
) {
    fun emotionToDouble(): Double {
        return when (emotion) {
            "Радостное" -> 1.0
            "Грустное" -> 0.9
            "Гневное" -> 0.8
            "Напряженное" -> 0.7
            "Сонное" -> 0.6
            "Спокойное" -> 0.5
            "Беспокойное" -> 0.4
            else -> 0.0
        }
    }
}
