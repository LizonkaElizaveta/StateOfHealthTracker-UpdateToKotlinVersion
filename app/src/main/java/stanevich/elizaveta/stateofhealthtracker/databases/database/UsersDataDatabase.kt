package stanevich.elizaveta.stateofhealthtracker.databases.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import stanevich.elizaveta.stateofhealthtracker.databases.dao.UsersDataDao
import stanevich.elizaveta.stateofhealthtracker.databases.entity.UsersData

@Database(entities = [UsersData::class], version = 2, exportSchema = false)
abstract class UsersDataDatabase : RoomDatabase() {

    abstract val statesDatabaseDao: UsersDataDao

    companion object {

        /* The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. */
        @Volatile
        private var INSTANCE: UsersDataDatabase? = null

        fun getInstance(contex: Context): UsersDataDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        contex.applicationContext,
                        UsersDataDatabase::class.java,
                        "users_database_history"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }

        }

    }
}