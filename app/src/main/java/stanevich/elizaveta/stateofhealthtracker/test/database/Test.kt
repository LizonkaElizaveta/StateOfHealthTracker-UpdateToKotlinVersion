package stanevich.elizaveta.stateofhealthtracker.test.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "test_image")
    val testImage: String = "",

    @ColumnInfo(name = "test_text")
    val testText: String = ""
)