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

    @ColumnInfo(name = "category")
    var notificationsCategory: String = "",

    @ColumnInfo(name = "date")
    var notificationsDate: String = "",

    @ColumnInfo(name = "repeat")
    var notificationRepeat: BooleanArray = BooleanArray(7){false}

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notifications

        if (notificatiionsId != other.notificatiionsId) return false
        if (notificationsTime != other.notificationsTime) return false
        if (notificationsCategory != other.notificationsCategory) return false
        if (notificationsDate != other.notificationsDate) return false
        if (!notificationRepeat.contentEquals(other.notificationRepeat)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = notificatiionsId.hashCode()
        result = 31 * result + notificationsTime.hashCode()
        result = 31 * result + notificationsCategory.hashCode()
        result = 31 * result + notificationsDate.hashCode()
        result = 31 * result + notificationRepeat.contentHashCode()
        return result
    }
}