package com.jordirubiralta.data.repository

import com.jordirubiralta.data.database.dao.UserDao
import com.jordirubiralta.data.datasource.UserLocalDataSource
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
    private val networkDataSource: UserNetworkDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {

    companion object {
        private const val RESULTS_PER_PAGE = 20
    }

    override suspend fun getUsers(): List<UserModel> = withContext(ioDispatcher) {
        (localDataSource.getAllUsers().takeUnless { it.isEmpty() } ?: getNetworkUsers()).apply {
            val deletedUsers = localDataSource.getDeletedUsers()
            this.filter { deletedUsers.contains(it.email) }
        }
    }

    override suspend fun deleteUser(email: String) {
        localDataSource.deleteUser(email = email)
    }

    // private methods
    private suspend fun getNetworkUsers(): List<UserModel> {
        val users = networkDataSource.getUsers(results = RESULTS_PER_PAGE).distinctBy { it.email }
        localDataSource.insertAllUsers(userList = users)
        return users
    }

}
