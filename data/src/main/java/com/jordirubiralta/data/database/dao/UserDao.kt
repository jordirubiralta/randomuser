package com.jordirubiralta.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jordirubiralta.data.database.entity.DeletedUserEntity
import com.jordirubiralta.data.database.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(userList: List<UserEntity>)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUser(email: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDeletedUser(user: DeletedUserEntity)

    @Query("SELECT * FROM deleted_users")
    suspend fun getAllDeletedUsers(): List<DeletedUserEntity>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?
}
