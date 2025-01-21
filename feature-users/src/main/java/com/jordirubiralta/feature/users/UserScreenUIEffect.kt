package com.jordirubiralta.feature.users

sealed class UserScreenUIEffect {
    data class DeleteItemSnackbar(val message: String) : UserScreenUIEffect()
}
