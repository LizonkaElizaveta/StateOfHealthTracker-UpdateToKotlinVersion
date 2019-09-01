package stanevich.elizaveta.stateofhealthtracker.databases.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States
import stanevich.elizaveta.stateofhealthtracker.databases.room.StatesDatabase

@Dao
interface StatesDatabaseDao {

    @Insert
    fun insert(state: States)

    @Update
    fun update(state:States)

    @Query("SELECT * from states_table WHERE statesId =:key")
    fun get(key:Long): States?

    @Query("DELETE from states_table")
    fun clear()

    @Query("SELECT * from states_table ORDER BY statesId DESC LIMIT 1")
    fun getLastState(): States?

    @Query("SELECT * FROM states_table ORDER BY statesId DESC")
    fun getAllStates(): LiveData<List<States>>

}