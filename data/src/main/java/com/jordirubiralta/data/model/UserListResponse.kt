package com.jordirubiralta.data.model

import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("results") val results: List<UserResponse>?,
    @SerializedName("info") val info: InfoResponse?
)
