package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SpeedDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(speed: Speed)

    @Query("SELECT * FROM speed_table")
    fun findAll(): List<Speed>

    @Query("DELETE from speed_table")
    fun clear()
}