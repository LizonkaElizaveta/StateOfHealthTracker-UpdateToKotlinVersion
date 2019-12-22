package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "miss_click_table")
data class MissClick(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0L,

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0L,

    @ColumnInfo(name = "count")
    var count: Int = 0,

    @ColumnInfo(name = "distance")
    var distance: Double = 0.0
)