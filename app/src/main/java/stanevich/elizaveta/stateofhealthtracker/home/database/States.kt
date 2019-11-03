package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "states_table")
data class States(
    @PrimaryKey(autoGenerate = true)
    var statesId: Long = 0L,

    @ColumnInfo(name = "date")
    var statesDate: Date = Date(),

    @ColumnInfo(name = "mood")
    var statesMood: String = "",

    @ColumnInfo(name = "pill")
    var statesPill: String = "",

    @ColumnInfo(name = "diskinezia")
    var statesDiskinezia: String = ""
)