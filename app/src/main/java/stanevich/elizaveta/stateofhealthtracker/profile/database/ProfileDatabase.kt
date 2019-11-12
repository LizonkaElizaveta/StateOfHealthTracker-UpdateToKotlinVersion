package stanevich.elizaveta.stateofhealthtracker.profile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 2, exportSchema = false)
abstract class ProfileDatabase : RoomDatabase() {

    abstract val statesDatabaseDao: ProfileDao

    companion object {

        /* The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. */
        @Volatile
        private var INSTANCE: ProfileDatabase? = null

        fun getInstance(contex: Context): ProfileDatabase {

            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        contex.applicationContext,
                        ProfileDatabase::class.java,
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