package stanevich.elizaveta.stateofhealthtracker.home.database

import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.NetworkDao
import java.util.*

@Dao
interface StatesDatabaseDao : NetworkDao<States> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: States)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(state: States)

    @Query("DELETE from states_table")
    override fun clear()

    @Query("SELECT * from states_table ORDER BY id DESC LIMIT 1")
    fun getLastState(): States?

    @Transaction
    fun upsert(state: States) {
        insert(state)
        update(state)
    }

    @Query("SELECT * from states_table WHERE date =:date")
    fun findByDate(date: Date): States?

    @Query("SELECT * from states_table")
    override fun findAll(): List<States>
}