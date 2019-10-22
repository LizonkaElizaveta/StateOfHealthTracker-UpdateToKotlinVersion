package stanevich.elizaveta.stateofhealthtracker.test.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TestDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(test: Test)

    @Query("DELETE FROM test_table")
    suspend fun deleteAll()

}