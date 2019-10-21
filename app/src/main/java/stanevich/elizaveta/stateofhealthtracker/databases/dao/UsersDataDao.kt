package stanevich.elizaveta.stateofhealthtracker.databases.dao

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
    fun upsert(user: UsersData) {
        insert(user)
        update(user)
    }

    @Query("SELECT * from users_table WHERE usersId = 1")
    fun findByUser(): UsersData?

}