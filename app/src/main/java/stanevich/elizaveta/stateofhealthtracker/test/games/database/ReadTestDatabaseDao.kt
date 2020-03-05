package stanevich.elizaveta.stateofhealthtracker.test.games.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.NetworkDao
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.read.model.ReadTest

@Dao
interface ReadTestDatabaseDao : NetworkDao<ReadTest>{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: ReadTest)

    @Query("SELECT * from read_test_table")
    override fun findAll(): List<ReadTest>

    @Query("DELETE from read_test_table")
    override fun clear()

}