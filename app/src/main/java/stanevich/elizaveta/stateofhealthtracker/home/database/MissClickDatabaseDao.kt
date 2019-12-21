package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MissClickDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(missClick: MissClick)

    @Query("SELECT * FROM miss_click_table")
    fun findAll(): List<MissClick>

    @Query("DELETE from miss_click_table")
    fun clear()
}