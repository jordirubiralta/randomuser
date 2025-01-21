package com.jordirubiralta.feature.users

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jordirubiralta.feature.users.components.UserCard
import kotlinx.coroutines.delay

@Composable
fun UsersScreen(
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    var someInputText by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = someInputText) {
        delay(500)
        viewModel.getUsers(someInputText.text)
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
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
                onRowClicked = { /*Navigate to detail*/ },
                onRemoveClicked = viewModel::deleteUser
            )
            if (index != state.userList.size.dec()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { /* block screen interactions while loading*/ }
                }
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onSurface,
                strokeWidth = 3.dp,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center)
                    .alpha(1f)
            )
        }
    }
}
