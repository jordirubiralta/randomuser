package com.jordirubiralta.feature.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
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
import com.jordirubiralta.feature.users.components.UserCard
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
    val lazyListState = rememberLazyListState()
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

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
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
        }

        itemsIndexed(state.userList) { index, model ->
            UserCard(
                uiModel = model,
                onRowClicked = { navigateToDetail(it) },
                onRemoveClicked = {
                    viewModel.deleteUser(email = it, context = context)
                }
            )
            if (index != state.userList.size.dec()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (state.userList.isEmpty()) {
            item {
                Text(stringResource(R.string.no_results))
            }
        }

        item {
            if (state.isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                )
            } else if (someInputText.text.isBlank()) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .background(MaterialTheme.colorScheme.background),
                    onClick = { viewModel.fetchMoreUsers() }
                ) {
                    Text(stringResource(R.string.load_more))
                }
            }
        }
    }
}
