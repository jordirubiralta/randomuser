package com.jordirubiralta.randomuser.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument


enum class Screen(val route: String) {
    LIST("list"),
    DETAIL("detail")
}

sealed class NavigationItem(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object UserList : NavigationItem(route = Screen.LIST.route)
    data object UserDetail : NavigationItem(
        route = "${Screen.DETAIL.route}/{${ArgParams.EMAIL}}",
        navArguments = listOf(
            navArgument(ArgParams.EMAIL) { type = NavType.StringType }
        )
    ) {
        fun createRoute(email: String) =
            "${Screen.DETAIL.route}/$email"
    }
}

private object ArgParams {
    const val EMAIL = "email"
}

