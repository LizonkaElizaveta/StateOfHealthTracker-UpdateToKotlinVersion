package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speed_table")
data class Speed(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0L,

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0L,

    @ColumnInfo(name = "speed")
    var speed: Float = 0.0f
)