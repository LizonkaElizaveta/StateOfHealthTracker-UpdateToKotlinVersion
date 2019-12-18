package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RotationDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(rotation: Rotation)

    @Query("SELECT * FROM rotation_table")
    fun findAll(): List<Rotation>?

    @Query("DELETE from rotation_table")
    fun clear()
}