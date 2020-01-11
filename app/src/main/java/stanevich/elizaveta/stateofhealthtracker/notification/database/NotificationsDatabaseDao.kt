package stanevich.elizaveta.stateofhealthtracker.notification.database

import androidx.room.*

@Dao
interface NotificationsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(notification: Notifications)

    @Query("DELETE FROM notifications_table WHERE id =:notId")
    fun deleteByNotificationId(notId: Long)

    @Query("SELECT * from notifications_table WHERE id =:key")
    fun get(key: Long): Notifications?

    @Query("DELETE from notifications_table")
    fun clear()

    @Query("SELECT * FROM notifications_table ORDER BY id DESC LIMIT 1")
    fun getLast(): Notifications

    @Query("SELECT * FROM notifications_table ORDER BY id DESC")
    fun getAllNotifications(): List<Notifications>

    @Transaction
    fun upsert(notification: Notifications) {
        insert(notification)
        update(notification)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun update(notification: Notifications)
}