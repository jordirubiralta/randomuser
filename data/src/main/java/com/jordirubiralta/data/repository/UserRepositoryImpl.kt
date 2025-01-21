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

    override suspend fun getUsers(search: String?): List<UserModel> = withContext(ioDispatcher) {
        val users = (localDataSource.getAllUsers().takeUnless { it.isEmpty() } ?: getNetworkUsers())
        return@withContext search?.let {
            users.filter { user ->
                user.name.contains(it, ignoreCase = true)
                        || user.name.contains(it, ignoreCase = true)
                        || user.name.contains(
                    it, ignoreCase = true
                )
            }
        } ?: users
    }

    override suspend fun deleteUser(email: String) {
        localDataSource.deleteUser(email = email)
    }

    // private methods
    private suspend fun getNetworkUsers(): List<UserModel> {
        val users = networkDataSource.getUsers(results = RESULTS_PER_PAGE).distinctBy { it.email }
        val deletedUsers = localDataSource.getDeletedUsers()
        val filteredList = users.filterNot { deletedUsers.contains(it.email) }
        localDataSource.insertAllUsers(userList = filteredList)
        return filteredList
    }

}
