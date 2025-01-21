package com.jordirubiralta.domain.repository

import com.jordirubiralta.domain.model.UserListModel

interface UserRepository {

    suspend fun fetchUsers(search: String?): UserListModel

    suspend fun fetchMoreUsers(search: String?, page: Int): UserListModel

    suspend fun deleteUser(email: String)

}
