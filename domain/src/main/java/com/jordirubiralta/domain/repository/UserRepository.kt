package com.jordirubiralta.domain.repository

import com.jordirubiralta.domain.model.UserModel

interface UserRepository {

    suspend fun getUsers(search: String?): List<UserModel>

    suspend fun deleteUser(email: String)

}
