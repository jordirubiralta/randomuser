package com.jordirubiralta.randomuser.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jordirubiralta.randomuser.navigation.AppNavHost
import com.jordirubiralta.randomuser.ui.theme.RandomUserTheme

@Composable
fun RandomUserApp() {
    RandomUserTheme {
        AppNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}

