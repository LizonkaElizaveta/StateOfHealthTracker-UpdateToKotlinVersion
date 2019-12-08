package stanevich.elizaveta.stateofhealthtracker.test.game.database

import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.test.game.tapping.TappingTest
import java.util.*

@Dao
interface TappingTestDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: TappingTest)

    @Query("SELECT * from tapping_test_table")
    fun findAll(): List<TappingTest>

}