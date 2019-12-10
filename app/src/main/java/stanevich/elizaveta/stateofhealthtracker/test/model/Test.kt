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
        Test(1, "print", "test_background_print", "text_test_printing"),
        Test(2, "tapping", "test_background_target", "text_test_target"),
        Test(3, "stars", "test_background_stars", "text_test_stars"),
        Test(4, "draw", "test_background_circle", "text_test_draw_figure"),
        Test(5, "balloon", "test_background_balloon", "text_test_burst_ball"),
        Test(6, "mole", "test_background_hole_mole", "text_test_mole")
    )
}

