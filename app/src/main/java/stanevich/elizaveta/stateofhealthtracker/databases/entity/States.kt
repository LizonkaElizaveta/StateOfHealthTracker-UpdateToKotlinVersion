package stanevich.elizaveta.stateofhealthtracker.databases.entity

import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.untils.DateConverters
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "states_table")
data class States(
    @PrimaryKey(autoGenerate = true)
    var statesId: Long = 0L,


    @ColumnInfo(name = "date")
    var statesDate: Date = Date(),

    @ColumnInfo(name = "time")
    var statesTime:Long = System.currentTimeMillis(),

    @ColumnInfo(name = "mood")
    var statesMood: String = "",

    @ColumnInfo(name = "pill")
    var statesPill: String = "",

    @ColumnInfo(name = "diskinezia")
    var statesDiskinezia: String = ""
)
