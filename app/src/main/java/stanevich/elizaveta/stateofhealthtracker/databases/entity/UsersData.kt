package stanevich.elizaveta.stateofhealthtracker.databases.entity

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
    var userLastName: String = "",

    @ColumnInfo(name = "email")
    var usersEmail: String = "",

    @ColumnInfo(name = "phone")
    var usersPhone: Int = 0

)
