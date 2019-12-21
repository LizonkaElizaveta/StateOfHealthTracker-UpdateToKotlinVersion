package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.NetworkDao

@Dao
interface SpeedDatabaseDao : NetworkDao<Speed> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(speed: Speed)

    @Query("SELECT * FROM speed_table")
    override fun findAll(): List<Speed>

    @Query("DELETE from speed_table")
    override fun clear()
}