package stanevich.elizaveta.stateofhealthtracker.databases.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.databases.room.NotificationsDatabase
import stanevich.elizaveta.stateofhealthtracker.databases.room.StatesDatabase

@Dao
interface NotificationsDatabaseDao {

    @Insert
    fun insert(notification: NotificationsDatabase)

    @Query("SELECT * from notifications_table WHERE notificatiionsId =:key")
    fun get(key:Long): NotificationsDatabase?

    @Query("DELETE from notifications_table")
    fun clear()

    @Query("SELECT * from notifications_table ORDER BY notificatiionsId DESC LIMIT 1")
    fun getLastState(): NotificationsDatabase?
}