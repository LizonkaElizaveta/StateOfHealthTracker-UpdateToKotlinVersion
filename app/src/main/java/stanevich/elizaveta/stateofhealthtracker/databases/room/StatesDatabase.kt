package stanevich.elizaveta.stateofhealthtracker.databases.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.StatesDatabaseDAO
import stanevich.elizaveta.stateofhealthtracker.databases.entity.States

@Database(entities = [States::class], version = 1, exportSchema = false)
abstract class StatesDatabase : RoomDatabase() {

    abstract val statesDatabaseDao: StatesDatabaseDAO

    companion object {

        /* The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. */
        @Volatile
        private var INSTANCE: StatesDatabase? = null

        fun getInstance(contex: Context): StatesDatabase {

            synchronized(this) {
                var instance = INSTANCE
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