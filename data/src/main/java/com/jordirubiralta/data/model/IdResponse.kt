package com.jordirubiralta.data.model

import com.google.gson.annotations.SerializedName

data class IdResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("value") val value: String?
)
