package com.jordirubiralta.data.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("street") val street: StreetResponse?,
    @SerializedName("city") val city: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("postcode") val postcode: String?
)
