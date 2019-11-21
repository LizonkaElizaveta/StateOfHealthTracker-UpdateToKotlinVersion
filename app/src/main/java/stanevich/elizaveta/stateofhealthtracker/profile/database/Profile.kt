package stanevich.elizaveta.stateofhealthtracker.profile.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class Profile(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "surname")
    var surname: String = "",

    @ColumnInfo(name = "birthday")
    var birthday: String = "",

    @ColumnInfo(name = "phone")
    var phone: String = ""

)
