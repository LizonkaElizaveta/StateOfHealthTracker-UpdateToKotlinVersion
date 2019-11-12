package stanevich.elizaveta.stateofhealthtracker.profile.database

import androidx.room.*

@Dao
interface ProfileDao {

    @Query("SELECT * from users_table WHERE usersId =:key")
    fun get(key: Long): Profile?

    @Query("DELETE from users_table")
    fun clear()

    @Query("SELECT * from users_table ORDER BY usersId DESC LIMIT 1")
    fun getLastUser(): Profile?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: Profile)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(user: Profile)

    @Transaction
    fun upsert(user: Profile) {
        insert(user)
        update(user)
    }

    @Query("SELECT * from users_table WHERE usersId = 1")
    fun findByUser(): Profile?

}