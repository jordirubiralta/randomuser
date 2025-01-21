package com.jordirubiralta.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jordirubiralta.data.database.dao.UserDao
import com.jordirubiralta.data.database.entity.DeletedUserEntity
import com.jordirubiralta.data.database.entity.UserEntity

@Database(
    entities = [UserEntity::class, DeletedUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
