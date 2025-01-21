package com.jordirubiralta.domain.repository

import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.model.UserModel

interface UserRepository {

    suspend fun fetchUsers(): UserListModel

    suspend fun fetchUsersFromNetwork(page: Int = 1): UserListModel

    suspend fun deleteUser(email: String)

    suspend fun getUserByEmail(email: String): UserModel?
}
