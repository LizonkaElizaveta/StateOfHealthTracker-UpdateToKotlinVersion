package stanevich.elizaveta.stateofhealthtracker.test.games.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import stanevich.elizaveta.stateofhealthtracker.test.games.print.model.PrintTest
import stanevich.elizaveta.stateofhealthtracker.test.games.tapping.model.TappingTest
import stanevich.elizaveta.stateofhealthtracker.utils.DateConverters


@Database(entities = [TappingTest::class, PrintTest::class], version = 2, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class TestingDatabase : RoomDatabase() {

    abstract val tappingTestDatabaseDao: TappingTestDatabaseDao
    abstract val printTestDatabaseDao: PrintTestDatabaseDao

    fun getAllNetworkDao() = listOf(tappingTestDatabaseDao, printTestDatabaseDao)

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