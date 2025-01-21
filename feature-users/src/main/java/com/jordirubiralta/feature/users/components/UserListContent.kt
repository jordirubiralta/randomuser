package com.jordirubiralta.feature.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jordirubiralta.feature.users.R
import com.jordirubiralta.feature.users.model.UserUIModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun UserListContent(
    userList: ImmutableList<UserUIModel>,
    isLoadingMore: Boolean,
    showLoadMore: Boolean,
    navigateToDetail: (String) -> Unit,
    deleteUser: (String) -> Unit,
    fetchMoreUsers: () -> Unit,
    modifier: Modifier = Modifier
) {

    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(userList) { index, model ->
            UserCard(
                uiModel = model,
                onRowClicked = { navigateToDetail(it) },
                onRemoveClicked = { deleteUser(it) }
            )
            if (index != userList.size.dec()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        item {
            if (isLoadingMore) {
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                )
            } else if (showLoadMore) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .background(MaterialTheme.colorScheme.background),
                    onClick = { fetchMoreUsers() }
                ) {
                    Text(stringResource(R.string.load_more))
                }
            }
        }
    }
}
