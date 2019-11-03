package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.*
import java.util.*

@Dao
interface StatesDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: States)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(state: States)

    @Query("DELETE from states_table")
    fun clear()

    @Query("SELECT * from states_table ORDER BY statesId DESC LIMIT 1")
    fun getLastState(): States?

    @Transaction
    fun upsert(state: States) {
        insert(state)
        update(state)
    }

    @Query("SELECT * from states_table WHERE date =:date")
    fun findByDate(date: Date): States?
}