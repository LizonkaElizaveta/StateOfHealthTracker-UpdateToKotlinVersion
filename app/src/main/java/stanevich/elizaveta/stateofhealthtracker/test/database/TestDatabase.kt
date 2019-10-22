package stanevich.elizaveta.stateofhealthtracker.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Test::class], version = 1, exportSchema = false)
abstract class TestDatabase : RoomDatabase() {

    abstract fun testDatabaseDao(): TestDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): TestDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TestDatabase::class.java,
                        "test_database_history"
                    ).addCallback(TestDatabaseCallback(scope))
                        .fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }


    private class TestDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var testDao = database.testDatabaseDao()

                    // Delete all content here.
                    testDao.deleteAll()

                    // Add sample test.
                    var test =
                        Test(1, "R.drawable.background_figure", "R.string.text_test_draw_figure")
                    test = Test(2, "R.drawable.background_ball", "R.string.text_test_burst_ball")
                    test = Test(3, "R.drawable.backgroun_mole", "R.string.text_test_mole")
                    testDao.insert(test)
                }
            }
        }
    }
}

