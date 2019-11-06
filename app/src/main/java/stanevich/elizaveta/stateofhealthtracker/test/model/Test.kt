package stanevich.elizaveta.stateofhealthtracker.test.model

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

fun populateData(): List<Test> {
    return listOf(
        Test(1, "background_circle", "text_test_draw_figure"),
        Test(2, "background_balloon", "text_test_burst_ball"),
        Test(3, "background_hole_mole", "text_test_mole"),
        Test(4, "background_target", "text_test_target")
    )
}

