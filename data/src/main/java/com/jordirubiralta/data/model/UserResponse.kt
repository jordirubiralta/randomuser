package com.jordirubiralta.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("gender") val gender: String?,
    @SerializedName("name") val name: NameResponse?,
    @SerializedName("location") val location: LocationResponse?,
    @SerializedName("email") val email: String?,
    @SerializedName("registered") val registered: RegisteredResponse?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("picture") val picture: PictureResponse?,
)
