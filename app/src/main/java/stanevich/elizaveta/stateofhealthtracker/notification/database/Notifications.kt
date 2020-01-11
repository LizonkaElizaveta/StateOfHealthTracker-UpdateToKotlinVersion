package stanevich.elizaveta.stateofhealthtracker.notification.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications_table")
data class Notifications(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "time")
    var time: String = "",

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

    @ColumnInfo(name = "repeat")
    var repeat: BooleanArray = BooleanArray(7) { false },

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0L

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notifications

        if (id != other.id) return false
        if (time != other.time) return false
        if (category != other.category) return false
        if (date != other.date) return false
        if (!repeat.contentEquals(other.repeat)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + repeat.contentHashCode()
        return result
    }
}