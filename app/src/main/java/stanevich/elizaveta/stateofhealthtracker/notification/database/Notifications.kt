package stanevich.elizaveta.stateofhealthtracker.notification.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications_table")
data class Notifications(
    @PrimaryKey(autoGenerate = true)
    var notificatiionsId: Long = 0L,

    @ColumnInfo(name = "time")
    var notificationsTime: String = "",

    @ColumnInfo(name = "text")
    var notificationsText: String = ""

)