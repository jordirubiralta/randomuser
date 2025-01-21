package com.jordirubiralta.domain.repository

import com.jordirubiralta.domain.model.UserListModel

interface UserRepository {

    suspend fun fetchUsers(): UserListModel

    suspend fun fetchUsersFromNetwork(page: Int = 1): UserListModel

    suspend fun deleteUser(email: String)

}
