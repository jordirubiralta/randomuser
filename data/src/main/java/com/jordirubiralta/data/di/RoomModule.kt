package com.jordirubiralta.data.di

import android.content.Context
import androidx.room.Room
import com.jordirubiralta.data.database.AppDataBase
import com.jordirubiralta.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "users_database"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DATABASE_NAME,
        ).build()
    }

    @Provides
    fun provideUserDao(dataBase: AppDataBase): UserDao {
        return dataBase.userDao()
    }

}
