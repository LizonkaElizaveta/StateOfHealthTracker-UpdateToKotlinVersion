package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.NetworkDao

@Dao
interface RotationDatabaseDao : NetworkDao<Rotation> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(rotation: Rotation)

    @Query("SELECT * FROM rotation_table")
    override fun findAll(): List<Rotation>

    @Query("DELETE from rotation_table")
    override fun clear()
}