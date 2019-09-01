package stanevich.elizaveta.stateofhealthtracker.databases.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import stanevich.elizaveta.stateofhealthtracker.databases.entity.UsersData
import stanevich.elizaveta.stateofhealthtracker.databases.room.UsersDataDatabase

@Dao
interface UsersDataDao {


    @Insert
    fun insert(notification: UsersData)

    @Query("SELECT * from users_table WHERE usersId =:key")
    fun get(key:Long): UsersData?

    @Query("DELETE from users_table")
    fun clear()

    @Query("SELECT * from users_table ORDER BY usersId DESC LIMIT 1")
    fun getLastUser(): UsersData?
}