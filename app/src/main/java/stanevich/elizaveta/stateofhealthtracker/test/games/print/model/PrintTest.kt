package stanevich.elizaveta.stateofhealthtracker.test.games.print.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "print_test_table")
data class PrintTest(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "original_text")
    var originalText: String = "",

    @ColumnInfo(name = "user_text")
    var userText: String = "",

    @ColumnInfo(name = "date")
    var date: Date = Date(),

    @ColumnInfo(name = "time")
    var time: Date = Date(),

    @ColumnInfo(name = "erased")
    var erased: Int = 0
)