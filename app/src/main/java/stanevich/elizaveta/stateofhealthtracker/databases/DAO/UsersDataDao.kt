package stanevich.elizaveta.stateofhealthtracker.databases.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import stanevich.elizaveta.stateofhealthtracker.databases.entity.UsersData

@Dao
interface UsersDataDao {

    @Query("SELECT * from users_table WHERE usersId =:key")
    fun get(key:Long): UsersData?

    @Query("DELETE from users_table")
    fun clear()

    @Query("SELECT * from users_table ORDER BY usersId DESC LIMIT 1")
    fun getLastUser(): UsersData?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UsersData)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(user: UsersData)

    @Transaction
    fun upsert(state: UsersData){
        insert(state)
        update(state)
    }

    @Query("SELECT * from users_table WHERE usersId = 1")
    fun findByUser(): UsersData?

}