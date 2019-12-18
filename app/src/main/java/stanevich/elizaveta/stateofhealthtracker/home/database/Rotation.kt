package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rotation_table")
data class Rotation(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0L,

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0L,

    @ColumnInfo(name = "pitch")
    var pitch: Float = 0.0f,

    @ColumnInfo(name = "azimuth")
    var azimuth: Float = 0.0f,

    @ColumnInfo(name = "roll")
    var roll: Float = 0.0f
)