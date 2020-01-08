package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "emotional_test_table")
data class EmotionalTest(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "amplitudes")
    var amplitudes: Array<Double> = arrayOf(),

    @ColumnInfo(name = "pathToFile")
    var path: String = "",

    @ColumnInfo(name = "date")
    var date: Long = Calendar.getInstance().timeInMillis
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmotionalTest

        if (id != other.id) return false
        if (!amplitudes.contentEquals(other.amplitudes)) return false
        if (path != other.path) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + amplitudes.contentHashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}

