package stanevich.elizaveta.stateofhealthtracker.test.games.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.test.games.print.model.PrintTest

interface PrintTestDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: PrintTest)

    @Query("SELECT * from print_test_table")
    fun findAll(): List<PrintTest>

}