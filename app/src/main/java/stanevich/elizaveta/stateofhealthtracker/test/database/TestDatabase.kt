package stanevich.elizaveta.stateofhealthtracker.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors


@Database(entities = [Test::class], version = 3)
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
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            Executors.newSingleThreadScheduledExecutor().execute {
                                getInstance(context).testDatabaseDao()
                                    .insertAll(*populateData())

                            }
                        }
                    })
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

fun populateData(): Array<Test> {
    return arrayOf(
        Test(1, "R.drawable.background_figure", "R.string.text_test_draw_figure"),
        Test(2, "R.drawable.background_ball", "R.string.text_test_burst_ball"),
        Test(3, "R.drawable.background_mole", "R.string.text_test_mole")
    )
}


//    private class TestDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    var testDao = database.testDatabaseDao()
//
//                    // Delete all content here.
//                    testDao.deleteAll()
//
//                    // Add sample test.
//                    var test =
//                        Test(1, "R.drawable.background_figure", "R.string.text_test_draw_figure")
//                    test = Test(2, "R.drawable.background_ball", "R.string.text_test_burst_ball")
//                    test = Test(3, "R.drawable.backgroun_mole", "R.string.text_test_mole")
//                    testDao.insert(test)
//                }
//            }
//        }
//    }
//}

