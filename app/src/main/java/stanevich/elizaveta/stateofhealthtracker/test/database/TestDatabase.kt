package stanevich.elizaveta.stateofhealthtracker.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Test::class], version = 1)
abstract class TestDatabase : RoomDatabase() {

    abstract fun testDatabaseDao(): TestDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getInstance(context: Context): TestDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TestDatabase::class.java,
                        "try_database_history"
                    )
//                        .addCallback(object : RoomDatabase.Callback() {
//                        override fun onOpen(db: SupportSQLiteDatabase) {
//                            super.onOpen(db)
//                            Executors.newSingleThreadScheduledExecutor().execute {
//                                getInstance(context).testDatabaseDao()
//                                    .insertAll(populateData())
//
//                            }
//                        }
//                    })
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

fun populateData(): List<Test> {
    return listOf(
        Test(1, "background_test_figure", "text_test_draw_figure"),
        Test(2, "background_test_burst_ball", "text_test_burst_ball"),
        Test(3, "background_test_mole", "text_test_mole"),
        Test(4, "background_test_target", "text_test_target")
    )
}

