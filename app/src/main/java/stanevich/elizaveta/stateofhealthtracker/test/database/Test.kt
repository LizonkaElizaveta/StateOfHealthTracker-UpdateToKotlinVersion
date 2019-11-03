package stanevich.elizaveta.stateofhealthtracker.test.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "image")
    var image: String = "",

    @ColumnInfo(name = "text")
    var text: String = ""
)

