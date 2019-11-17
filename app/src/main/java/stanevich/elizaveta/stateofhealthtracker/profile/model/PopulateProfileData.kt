package stanevich.elizaveta.stateofhealthtracker.profile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "populate_data")
data class PopulateProfileData(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "description")
    var description: String = ""
)

fun populateData(): List<PopulateProfileData> {
    return listOf(
        PopulateProfileData(
            1,
            "profile_text_fio",
            "profile_text_user_name"
        ),
        PopulateProfileData(
            2,
            "profile_text_birthday",
            "profile_text_data_birthday"
        ),
        PopulateProfileData(
            3,
            "profile_text_phone",
            "profile_text_number_phone"
        ),
        PopulateProfileData(
            4,
            "profile_text_mail",
            "profile_text_number_mail"
        )
    )
}
