package com.jordirubiralta.data.datasource

import android.util.Log
import com.jordirubiralta.data.api.UserApi
import com.jordirubiralta.data.mapper.UserMapper
import com.jordirubiralta.domain.model.UserListModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserNetworkDataSource @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun getUsers(results: Int, page: Int): UserListModel =
        try {
            val response = userApi.getUsers(results = results, page = page)
            UserMapper.fromUserListResponseToModel(response)
        } catch (e: Exception) {
            // Manage exception
            Log.e("UserNetworkDataSource", "Error fetching users")
            UserListModel()
        }

}
