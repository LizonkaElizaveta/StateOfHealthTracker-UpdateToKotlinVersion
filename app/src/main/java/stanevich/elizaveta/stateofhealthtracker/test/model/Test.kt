package stanevich.elizaveta.stateofhealthtracker.test.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "image")
    val image: String = "",

    @ColumnInfo(name = "text")
    val text: String = ""
)

fun populateData(): List<Test> {
    return listOf(
        Test(1, "draw", "test_background_circle", "text_test_draw_figure"),
        Test(2,"balloon","test_background_balloon", "text_test_burst_ball"),
        Test(3,"reading","test_background_reading", "text_test_reading"),
//        Test(3, "",, "test_background_hole_mole", "text_test_mole"),
        Test(4,"tapping","test_background_target", "text_test_target")
    )
}

