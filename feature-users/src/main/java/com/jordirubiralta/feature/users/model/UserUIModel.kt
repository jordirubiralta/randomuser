package com.jordirubiralta.feature.users.model

data class UserUIModel(
    val name: String,
    val surname: String,
    val email: String,
    val imageUrl: String?,
    val phone: String?
)
