package com.jordirubiralta.randomuser.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class Screen(route: String) {
    LIST("list"),
    DETAIL("detail/{${ArgParams.EMAIL}}")
}

sealed class NavigationItem(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object UsersList : NavigationItem(route = Screen.LIST.name)
    data object UserDetail : NavigationItem(
        route = Screen.DETAIL.name, navArguments = listOf(
            navArgument(ArgParams.EMAIL) {
                type = NavType.Companion.StringType
            })
    ) {
        fun createRoute(email: String) =
            Screen.DETAIL.name.replace(ArgParams.toPath(ArgParams.EMAIL), email)
    }
}

private object ArgParams {
    const val EMAIL = "email"

    fun toPath(param: String) = "{$param}"
}


