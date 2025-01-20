package com.jordirubiralta.data.di

import com.jordirubiralta.data.repository.UserRepositoryImpl
import com.jordirubiralta.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        impl: UserRepositoryImpl
    ): UserRepository

}
