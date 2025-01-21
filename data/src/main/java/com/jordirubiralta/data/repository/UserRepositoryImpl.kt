package com.jordirubiralta.data.repository

import com.jordirubiralta.data.datasource.UserNetworkDataSource
import com.jordirubiralta.data.di.IoDispatcher
import com.jordirubiralta.domain.model.UserModel
import com.jordirubiralta.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val networkDataSource: UserNetworkDataSource
) : UserRepository {

    override suspend fun getUsers(results: Int): List<UserModel> = withContext(ioDispatcher) {
        networkDataSource.getUsers(results = results)
    }
}
