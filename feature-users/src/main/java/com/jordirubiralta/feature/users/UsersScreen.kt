package com.jordirubiralta.feature.users

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UsersScreen(
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }
    Text("Hello World")
}
