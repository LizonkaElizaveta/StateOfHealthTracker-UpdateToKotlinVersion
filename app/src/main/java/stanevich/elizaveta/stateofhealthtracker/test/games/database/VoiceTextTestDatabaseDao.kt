package stanevich.elizaveta.stateofhealthtracker.test.games.database

import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.network.api.dataStore.NetworkDao
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.model.EmotionalTest
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.text.model.VoiceTextTest

@Dao
interface VoiceTextTestDatabaseDao : NetworkDao<VoiceTextTest>{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(state: VoiceTextTest)

    @Query("SELECT * from voice_text_test_table")
    override fun findAll(): List<VoiceTextTest>

    @Query("DELETE from voice_text_test_table")
    override fun clear()

}