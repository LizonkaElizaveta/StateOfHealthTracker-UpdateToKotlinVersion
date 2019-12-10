package stanevich.elizaveta.stateofhealthtracker.test.games.database

import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.TappingTest

@Dao
interface TappingTestDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: TappingTest)

    @Query("SELECT * from tapping_test_table")
    fun findAll(): List<TappingTest>

}