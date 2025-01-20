package com.jordirubiralta.data.datasource

import android.util.Log
import com.jordirubiralta.data.api.UserApi
import com.jordirubiralta.data.mapper.UserMapper
import com.jordirubiralta.domain.model.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserNetworkDataSource @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun getUsers(result: Int): List<UserModel> =
        try {
            val response = userApi.getUsers(result = result)
            UserMapper.fromUserListResponseToModel(response)
        } catch (e: Exception) {
            // Manage exception
            Log.e("UserNetworkDataSource", "Error fetching users")
            Log.e("UserNetworkDataSource", e.message.toString())
            emptyList()
        }

}
