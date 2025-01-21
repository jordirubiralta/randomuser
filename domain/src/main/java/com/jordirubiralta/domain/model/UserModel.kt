package com.jordirubiralta.domain.model

data class UserModel(
    val gender: String?,
    val title: String?,
    val name: String,
    val surname: String,
    val email: String,
    val thumbnailImageUrl: String?,
    val largeImageUrl: String?,
    val phone: String?,
    val location: String?,
    val registreredDate: String?
)
