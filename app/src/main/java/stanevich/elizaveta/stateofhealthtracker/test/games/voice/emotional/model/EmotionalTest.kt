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
    var amplitudes: Array<Double>? = null,

    @ColumnInfo(name = "path_to_file")
    var path: String? = null,

    @ColumnInfo(name = "date")
    var date: Long = Calendar.getInstance().timeInMillis
)

