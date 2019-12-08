package stanevich.elizaveta.stateofhealthtracker.notification.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkbox_table")
data class CheckBoxModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "btnDrawable")
    var btnDrawable: String = "",

    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean = false
)

fun populateData(): List<CheckBoxModel> {
    return listOf(
        CheckBoxModel(1, "notification_settings_ic_monday", false),
        CheckBoxModel(2, "notification_settings_ic_tuesday", false),
        CheckBoxModel(3, "notification_settings_ic_wednesday", false),
        CheckBoxModel(4, "notification_settings_ic_thursday", false),
        CheckBoxModel(5, "notification_settings_ic_friday", false),
        CheckBoxModel(6, "notification_settings_ic_saturday", false),
        CheckBoxModel(7, "notification_settings_ic_sunday", false)
    )
}