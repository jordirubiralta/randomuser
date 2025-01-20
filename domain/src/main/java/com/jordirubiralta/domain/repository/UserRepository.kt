package com.jordirubiralta.domain.repository

import com.jordirubiralta.domain.model.UserModel

interface UserRepository {

    suspend fun getUsers(result: Int): List<UserModel>

}
