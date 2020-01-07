package stanevich.elizaveta.stateofhealthtracker.test.games.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import stanevich.elizaveta.stateofhealthtracker.test.games.print.model.PrintTest
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.model.TappingTest
import stanevich.elizaveta.stateofhealthtracker.test.games.voice.emotional.model.EmotionalTest
import stanevich.elizaveta.stateofhealthtracker.utils.DateConverters
import stanevich.elizaveta.stateofhealthtracker.utils.DoubleArrayConverters


@Database(entities = [TappingTest::class, PrintTest::class, EmotionalTest::class], version = 4, exportSchema = false)
@TypeConverters(DateConverters::class, DoubleArrayConverters::class)
abstract class TestingDatabase : RoomDatabase() {

    abstract val tappingTestDatabaseDao: TappingTestDatabaseDao
    abstract val printTestDatabaseDao: PrintTestDatabaseDao
    abstract val emotionalTestDatabaseDao: EmotionalTestDatabaseDao

    fun getAllNetworkDao() = listOf(tappingTestDatabaseDao, printTestDatabaseDao, emotionalTestDatabaseDao)

    companion object {

        /* The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. */
        @Volatile
        private var INSTANCE: TestingDatabase? = null

        fun getInstance(context: Context): TestingDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TestingDatabase::class.java,
                        "testing_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }

        }

    }

}