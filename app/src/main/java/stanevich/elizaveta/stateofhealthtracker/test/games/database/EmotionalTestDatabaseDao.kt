package stanevich.elizaveta.stateofhealthtracker.test.games.database

import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.NetworkDao
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.model.EmotionalTest

@Dao
interface EmotionalTestDatabaseDao : NetworkDao<EmotionalTest>{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: EmotionalTest)

    @Query("SELECT * from emotional_test_table")
    override fun findAll(): List<EmotionalTest>

    @Query("DELETE from emotional_test_table")
    override fun clear()

}