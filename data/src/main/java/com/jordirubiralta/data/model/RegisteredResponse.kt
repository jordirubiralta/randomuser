package com.jordirubiralta.data.model

import com.google.gson.annotations.SerializedName

data class RegisteredResponse(
    @SerializedName("date") val date: String?,
    @SerializedName("age") val age: Int?
)
