package stanevich.elizaveta.stateofhealthtracker.test.games.database

import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.EmotionalTest

@Dao
interface EmotionalTestDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: EmotionalTest)

    @Query("SELECT * from tapping_test_table")
    fun findAll(): List<EmotionalTest>
}