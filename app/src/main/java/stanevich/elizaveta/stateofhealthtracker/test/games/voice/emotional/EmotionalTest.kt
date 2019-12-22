package stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "emotional_test")
data class EmotionalTest(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "amplitudes")
    var taps: Array<Double>? = null,

    @ColumnInfo(name = "path_to_file")
    var path: String? = null,

    @ColumnInfo(name = "date")
    var date: Date = Date()
)

