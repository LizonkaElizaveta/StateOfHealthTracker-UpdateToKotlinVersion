package stanevich.elizaveta.stateofhealthtracker.databases.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import java.util.*

@Dao
interface StatesDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: States)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(state: States)

    @Query("SELECT * from states_table WHERE statesId =:key")
    fun get(key: Long): States?

    @Query("DELETE from states_table")
    fun clear()

    @Query("SELECT * from states_table ORDER BY statesId DESC LIMIT 1")
    fun getLastState(): States?

    @Query("SELECT * FROM states_table ORDER BY statesId DESC")
    fun getAllStates(): LiveData<List<States>>

    @Query("UPDATE states_table SET pill =:pill WHERE date =:date")
    fun updatePill(pill: String, date: Date)

    @Query("UPDATE states_table SET diskinezia =:diskinezia WHERE date =:date")
    fun updateDiskinezia(diskinezia: String, date: Date)

    @Transaction
    fun upsert(state: States){
        insert(state)
        update(state)
    }

    @Query("SELECT * from states_table WHERE date =:date")
    fun findByDate(date: Date): States?
}