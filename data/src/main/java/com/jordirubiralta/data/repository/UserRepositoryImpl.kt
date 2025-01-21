package com.jordirubiralta.data.repository

import com.jordirubiralta.data.datasource.UserLocalDataSource
import com.jordirubiralta.data.datasource.UserNetworkDataSource
import com.jordirubiralta.data.di.IoDispatcher
import com.jordirubiralta.domain.model.UserListModel
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

    override suspend fun fetchUsers(): UserListModel = withContext(ioDispatcher) {
        localDataSource.getAllUsers().takeUnless { it.userList.isEmpty() }
            ?: fetchUsersFromNetwork()
    }

    override suspend fun fetchUsersFromNetwork(page: Int): UserListModel =
        withContext(ioDispatcher) {
            val userListModel = networkDataSource.getUsers(results = RESULTS_PER_PAGE, page = page)

            val filteredList = filterDeletedUsers(userListModel.userList)
            localDataSource.insertAllUsers(userList = filteredList)
            return@withContext userListModel.copy(userList = filteredList)
        }

    override suspend fun deleteUser(email: String) = withContext(ioDispatcher) {
        localDataSource.deleteUser(email = email)
    }

    private suspend fun filterDeletedUsers(users: List<UserModel>): List<UserModel> {
        val deletedUsers = localDataSource.getDeletedUsers()
        return users.filterNot { deletedUsers.contains(it.email) }
    }
}
