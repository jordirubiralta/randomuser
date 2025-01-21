package com.jordirubiralta.data.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("street") val street: StreetResponse?,
    @SerializedName("city") val city: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("country") val country: String?,
) {
    override fun toString() = buildString {
        var isFirst = true
        street?.name?.let {
            isFirst = false
            append(it)
        }
        street?.number?.let {
            if (!isFirst) {
                append(" ")
                isFirst = false
            }
            append(it)
        }
        city?.let {
            if (!isFirst) {
                append(", ")
                isFirst = false
            }
            append(it)
        }
        state?.let {
            if (!isFirst) {
                append(", ")
                isFirst = false
            }
            append(it)
        }
        country?.let {
            if (!isFirst) {
                append(", ")
            }
            append(it)
        }
    }


}
