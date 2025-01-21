package com.jordirubiralta.data.model

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("results") val results: Int?,
    @SerializedName("page") val page: Int?
)
