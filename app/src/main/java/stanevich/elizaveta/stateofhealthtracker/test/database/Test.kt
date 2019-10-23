package stanevich.elizaveta.stateofhealthtracker.test.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_table")
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

