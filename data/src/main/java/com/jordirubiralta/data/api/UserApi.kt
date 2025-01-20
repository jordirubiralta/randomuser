package com.jordirubiralta.data.api

import com.jordirubiralta.data.model.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/")
    suspend fun getUsers(
        @Query("result") result: Int
    ): UserListResponse

}
