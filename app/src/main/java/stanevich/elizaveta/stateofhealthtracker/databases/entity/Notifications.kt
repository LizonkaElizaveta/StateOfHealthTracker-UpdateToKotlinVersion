package stanevich.elizaveta.stateofhealthtracker.databases.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications_table")
data class Notifications(
    @PrimaryKey(autoGenerate = true)
    var notificatiionsId: Long = 0L,

    @ColumnInfo(name = "time")
    var notificationsTime: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "text")
    var notificationsText: String = ""

)