package stanevich.elizaveta.stateofhealthtracker.notification.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkbox_table")
data class CheckBoxModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "btnDrawable")
    var btnDrawable: String = ""

)

fun populateData(): List<CheckBoxModel> {
    return listOf(
        CheckBoxModel(1, "notification_settings_ic_monday"),
        CheckBoxModel(2, "notification_settings_ic_tuesday"),
        CheckBoxModel(3, "notification_settings_ic_wednesday"),
        CheckBoxModel(4, "notification_settings_ic_thursday"),
        CheckBoxModel(5, "notification_settings_ic_friday"),
        CheckBoxModel(6, "notification_settings_ic_saturday"),
        CheckBoxModel(7, "notification_settings_ic_sunday")
    )
}