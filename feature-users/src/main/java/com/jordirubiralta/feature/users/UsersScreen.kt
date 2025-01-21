package com.jordirubiralta.feature.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.jordirubiralta.feature.users.components.UserListContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UsersScreen(
    navigateToDetail: (String) -> Unit,
    snackbarState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var someInputText by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = Unit) {
        withContext(Dispatchers.Main) {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { value ->
                    when (value) {
                        is UserScreenUIEffect.DeleteItemSnackbar -> {
                            coroutineScope.launch {
                                snackbarState.showSnackbar(message = value.message)
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = someInputText) {
        delay(500)
        viewModel.fetchUsers(someInputText.text)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = someInputText,
            onValueChange = {
                someInputText = it
            },
            label = { Text(text = stringResource(R.string.filter)) },
            placeholder = {
                Text(
                    text = stringResource(R.string.input_hint),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            },
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = { someInputText = TextFieldValue("") },
                    content = {
                        Icon(
                            painter = painterResource(R.drawable.close),
                            contentDescription = stringResource(R.string.close)
                        )
                    }
                )
            }
        )
        when (state) {
            is UsersScreenState.Empty -> {
                Text(
                    text = stringResource(R.string.no_results),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            is UsersScreenState.Error -> {
                Text(
                    text = (state as UsersScreenState.Error).message
                        ?: stringResource(R.string.generic_error),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            is UsersScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        strokeWidth = 3.dp,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(40.dp)
                    )
                }
            }

            is UsersScreenState.Success -> {
                UserListContent(
                    userList = (state as UsersScreenState.Success).userList,
                    isLoadingMore = (state as UsersScreenState.Success).isLoadingMore,
                    showLoadMore = (state as UsersScreenState.Success).showLoadMore,
                    navigateToDetail = { navigateToDetail(it) },
                    deleteUser = { viewModel.deleteUser(email = it, context = context) },
                    fetchMoreUsers = { viewModel.fetchMoreUsers() },
                )
            }
        }
    }

}
