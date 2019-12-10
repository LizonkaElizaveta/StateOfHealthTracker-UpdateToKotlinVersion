package stanevich.elizaveta.stateofhealthtracker.test.games.tapping

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tapping_test_table")
data class TappingTest(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "taps")
    var taps: Int = 0,

    @ColumnInfo(name = "date")
    var date: Date = Date()
)

