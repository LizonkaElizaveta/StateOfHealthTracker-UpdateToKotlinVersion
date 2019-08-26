package stanevich.elizaveta.stateofhealthtracker.databases.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import stanevich.elizaveta.stateofhealthtracker.databases.room.StatesDatabase

@Dao
interface StatesDatabaseDAO {

    @Insert
    fun insert(state: StatesDatabase)

    @Update
    fun update(state:StatesDatabase)

    @Query("SELECT * from states_table WHERE statesId =:key")
    fun get(key:Long): StatesDatabase?

    @Query("DELETE from states_table")
    fun clear()

    @Query("SELECT * from states_table ORDER BY statesId DESC LIMIT 1")
    fun getLastState(): StatesDatabase?
    
}