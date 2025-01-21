package com.jordirubiralta.feature.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    email: String,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        // viewModel.getUserDetail(email = email)
    }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}
