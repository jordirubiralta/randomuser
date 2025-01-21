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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jordirubiralta.feature.users.components.UserCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()
    val snackbarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var someInputText by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = someInputText) {
        delay(500)
        viewModel.fetchUsers(someInputText.text)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.user_list_title)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                },
                content = {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        painter = painterResource(R.drawable.arrow_up),
                        contentDescription = stringResource(R.string.scroll_up)
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
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
                        onRemoveClicked = viewModel::deleteUser
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
    )
}
