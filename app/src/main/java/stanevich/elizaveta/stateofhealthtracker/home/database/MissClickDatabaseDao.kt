package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.*

@Dao
interface MissClickDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(missClick: MissClick)

    @Query("SELECT * FROM miss_click_table")
    fun findAll(): List<MissClick>?

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(state: States)

    @Query("DELETE from states_table")
    fun clear()
}