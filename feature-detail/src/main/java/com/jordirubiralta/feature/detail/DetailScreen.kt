package com.jordirubiralta.feature.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jordirubiralta.feature.detail.component.DetailContent

@Composable
fun DetailScreen(
    email: String,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserByEmail(email = email)
    }
    when (state) {
        is DetailScreenState.Success -> {
            DetailContent(
                modifier = modifier.fillMaxSize(),
                user = (state as DetailScreenState.Success).user
            )
        }

        is DetailScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }
        }

        is DetailScreenState.Empty -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = stringResource(R.string.empty_detail))
            }
        }

        is DetailScreenState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = (state as DetailScreenState.Error).message
                        ?: stringResource(R.string.generic_error)
                )
            }
        }
    }
}

