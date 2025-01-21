package com.jordirubiralta.randomuser.navigation

import androidx.compose.material3.SnackbarHostState
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
    snackbarHostState: SnackbarHostState,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.UserList.route
    ) {
        composable(NavigationItem.UserList.route) {
            UsersScreen(
                navigateToDetail = { email ->
                    val route = NavigationItem.UserDetail.createRoute(email = email)
                    navController.navigate(route)
                },
                snackbarState = snackbarHostState
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
