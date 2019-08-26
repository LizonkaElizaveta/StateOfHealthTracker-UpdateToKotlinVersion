package stanevich.elizaveta.stateofhealthtracker.databases.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.databases.room.UsersDataDatabase

@Dao
interface UsersDataDao {


    @Insert
    fun insert(notification: UsersDataDatabase)

    @Query("SELECT * from users_table WHERE usersId =:key")
    fun get(key:Long): UsersDataDatabase?

    @Query("DELETE from users_table")
    fun clear()

    @Query("SELECT * from users_table ORDER BY usersId DESC LIMIT 1")
    fun getLastUser(): UsersDataDatabase?
}