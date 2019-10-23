package stanevich.elizaveta.stateofhealthtracker.test.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TestDatabaseDao {

    @Query("SELECT * FROM test_table")
    fun getAll(): List<Test>

    @Insert
    fun insertAll(test: List<Test>)

}