package stanevich.elizaveta.stateofhealthtracker.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UsersData (

    @PrimaryKey(autoGenerate = true)
    var usersId: Long = 0L,

    @ColumnInfo(name = "firstName")
    var usersFirstName: String = "",

    @ColumnInfo(name = "lastName")
    var userSurname: String = "",

    @ColumnInfo(name = "email")
    var usersEmail: String = "",

    @ColumnInfo(name = "phone")
    var usersPhone: String = ""

)
