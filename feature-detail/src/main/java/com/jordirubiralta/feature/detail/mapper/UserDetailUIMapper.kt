package com.jordirubiralta.feature.detail.mapper

import com.jordirubiralta.domain.model.UserModel
import com.jordirubiralta.feature.detail.model.UserDetailUIModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object UserDetailUIMapper {

    private const val DEFAULT_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val TIMEZONE = "UTC"
    private const val DATE_FORMAT = "MM/dd/yyyy"


    fun fromUserModelToUserDetailUIModel(model: UserModel) = UserDetailUIModel(
        gender = model.gender,
        name = model.name,
        surname = model.surname,
        location = model.location,
        registeredDate = model.registreredDate?.let { formatDate(it) },
        email = model.email,
        imageUrl = model.largeImageUrl,
        phone = model.phone
    )

    private fun formatDate(inputDate: String): String? {
        val inputFormatter = SimpleDateFormat(DEFAULT_DATE, Locale.getDefault())
        inputFormatter.timeZone = TimeZone.getTimeZone(TIMEZONE)
        val date: Date? = inputFormatter.parse(inputDate)
        val outputFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return date?.let { outputFormatter.format(it) }
    }
}
