package com.jordirubiralta.data.repository

import com.jordirubiralta.data.datasource.UserLocalDataSource
import com.jordirubiralta.data.datasource.UserNetworkDataSource
import com.jordirubiralta.data.di.IoDispatcher
import com.jordirubiralta.domain.model.UserListModel
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

    override suspend fun fetchUsers(search: String?): UserListModel =
        withContext(ioDispatcher) {
            val userListModel = (localDataSource.getAllUsers().takeUnless { it.userList.isEmpty() }
                ?: getNetworkUsers())
            return@withContext search?.takeUnless { it.isBlank() }?.let {
                userListModel.filterList(it)
            } ?: userListModel
        }

    override suspend fun fetchMoreUsers(search: String?, page: Int): UserListModel =
        withContext(ioDispatcher) {
            val userListModel = getNetworkUsers(page = page)
            return@withContext search?.takeUnless { it.isBlank() }?.let {
                userListModel.filterList(it)
            } ?: userListModel
        }

    override suspend fun deleteUser(email: String) = withContext(ioDispatcher) {
        localDataSource.deleteUser(email = email)
    }

    // private methods
    private suspend fun getNetworkUsers(page: Int = 1): UserListModel {
        val userListModel = networkDataSource.getUsers(results = RESULTS_PER_PAGE, page = page)

        val deletedUsers = localDataSource.getDeletedUsers()
        val filteredList = userListModel.userList.filterNot { deletedUsers.contains(it.email) }
        localDataSource.insertAllUsers(userList = filteredList)

        return userListModel.copy(userList = filteredList)
    }

}
