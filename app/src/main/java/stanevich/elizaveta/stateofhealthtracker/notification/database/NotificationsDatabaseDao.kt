package stanevich.elizaveta.stateofhealthtracker.notification.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotificationsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(notification: Notifications)

    @Query("SELECT * from notifications_table WHERE notificatiionsId =:key")
    fun get(key: Long): Notifications?

    @Query("DELETE from notifications_table")
    fun clear()

/*
    @Query("SELECT * from notifications_table ORDER BY notificatiionsId DESC LIMIT 1")
    fun getLastNotification(): Notifications?
*/

    @Query("SELECT * FROM notifications_table ORDER BY notificatiionsId DESC")
    fun getAllNotifications(): LiveData<List<Notifications>>

    @Transaction
    fun upsert(notification: Notifications) {
        insert(notification)
        update(notification)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun update(notification: Notifications)
}