package com.jordirubiralta.randomuser.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jordirubiralta.feature.users.UsersScreen
import com.jordirubiralta.randomuser.ui.theme.RandomUserTheme

@Composable
fun RandomUserApp() {
    RandomUserTheme {
        UsersScreen()
    }
}
