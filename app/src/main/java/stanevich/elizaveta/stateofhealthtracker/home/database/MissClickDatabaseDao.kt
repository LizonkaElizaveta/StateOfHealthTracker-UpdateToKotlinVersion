package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.NetworkDao

@Dao
interface MissClickDatabaseDao : NetworkDao<MissClick> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(missClick: MissClick)

    @Query("SELECT * FROM miss_click_table")
    override fun findAll(): List<MissClick>

    @Query("DELETE from miss_click_table")
    override fun clear()
}