package stanevich.elizaveta.stateofhealthtracker.home.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import stanevich.elizaveta.stateofhealthtracker.utils.DateConverters


@Database(entities = [States::class], version = 2, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class StatesDatabase : RoomDatabase() {

    abstract val statesDatabaseDao: StatesDatabaseDao

    companion object {

        /* The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. */
        @Volatile
        private var INSTANCE: StatesDatabase? = null

        fun getInstance(contex: Context): StatesDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        contex.applicationContext,
                        StatesDatabase::class.java,
                        "states_database_history"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }

        }

    }

}