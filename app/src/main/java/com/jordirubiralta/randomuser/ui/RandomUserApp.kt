package com.jordirubiralta.randomuser.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.jordirubiralta.randomuser.R
import com.jordirubiralta.randomuser.navigation.AppNavHost
import com.jordirubiralta.randomuser.navigation.NavigationItem
import com.jordirubiralta.randomuser.ui.theme.RandomUserTheme

@Composable
fun RandomUserApp(
    appState: AppState = rememberAppState()
) {
    RandomUserTheme {
        Scaffold(
            topBar = {
                AppTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    currentRoute = appState.currentRoute,
                    onBackPressed = { appState.navController.popBackStack() }
                )
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = appState.snackbarHostState,
                )
            }
        ) { paddingValues ->
            AppNavHost(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                navController = appState.navController,
                snackbarHostState = appState.snackbarHostState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    currentRoute: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (currentRoute) {
        NavigationItem.UsersList.route -> {
            CenterAlignedTopAppBar(
                modifier = modifier,
                title = { Text(stringResource(R.string.user_list_title)) }
            )
        }

        NavigationItem.UserDetail.route -> {
            CenterAlignedTopAppBar(
                modifier = modifier,
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPressed() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_right),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = { Text(stringResource(R.string.user_detail_title)) }
            )
        }
    }

}

