package stanevich.elizaveta.stateofhealthtracker.notification.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkbox_table")
data class CheckBox(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "btnUncheck")
    var btnUncheck: String = "",

    @ColumnInfo(name = "btnCheck")
    var btnCheck: String = ""
)

fun populateData(): List<CheckBox> {
    return listOf(
        CheckBox(1, "notification_settings_ic_monday", "notification_settings_ic_monday_full"),
        CheckBox(2, "notification_settings_ic_tuesday", "notification_settings_ic_tuesday_full"),
        CheckBox(3, "notification_settings_ic_wednesday", "notification_settings_ic_wednesday_full"),
        CheckBox(4, "notification_settings_ic_thursday", "notification_settings_ic_thursday_full"),
        CheckBox(5, "notification_settings_ic_friday",  "notification_settings_ic_friday_full"),
        CheckBox(6, "notification_settings_ic_saturday", "notification_settings_ic_saturday_full"),
        CheckBox(7, "notification_settings_ic_sunday", "notification_settings_ic_sunday_full")
    )
}