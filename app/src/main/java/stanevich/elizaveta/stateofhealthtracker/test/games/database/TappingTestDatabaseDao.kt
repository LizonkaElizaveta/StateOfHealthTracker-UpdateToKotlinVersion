package stanevich.elizaveta.stateofhealthtracker.test.games.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.NetworkDao
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.model.TappingTest

@Dao
interface TappingTestDatabaseDao : NetworkDao<TappingTest> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: TappingTest)

    @Query("SELECT * from tapping_test_table")
    override fun findAll(): List<TappingTest>

    @Query("DELETE from tapping_test_table")
    override fun clear()
}