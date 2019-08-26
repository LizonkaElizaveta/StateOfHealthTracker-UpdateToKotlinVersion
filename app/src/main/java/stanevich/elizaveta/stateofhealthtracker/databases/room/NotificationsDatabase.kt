package stanevich.elizaveta.stateofhealthtracker.databases.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import stanevich.elizaveta.stateofhealthtracker.databases.DAO.NotificationsDatabaseDao
import stanevich.elizaveta.stateofhealthtracker.databases.entity.Notifications

@Database(entities = [Notifications::class] , version = 1, exportSchema = false)
abstract class NotificationsDatabase:RoomDatabase(){

    abstract val notificationsDatabaseDao: NotificationsDatabaseDao

    companion object {

        /* The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. */
        @Volatile
        private var INSTANCE: NotificationsDatabase? = null

        fun getInstance(contex: Context): NotificationsDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        contex.applicationContext,
                        NotificationsDatabase::class.java,
                        "notifications_database_history"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }

        }

    }
}