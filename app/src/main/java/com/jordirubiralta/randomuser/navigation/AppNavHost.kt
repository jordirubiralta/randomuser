package com.jordirubiralta.randomuser.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jordirubiralta.feature.detail.DetailScreen
import com.jordirubiralta.feature.users.UsersScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.UsersList.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.UsersList.route) {
            UsersScreen(
                navigateToDetail = { email ->
                    val route = NavigationItem.UserDetail.createRoute(email = email)
                    navController.navigate(route)
                }
            )
        }
        composable(NavigationItem.UserDetail.route) { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email").orEmpty()
            DetailScreen(
                email = email,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
