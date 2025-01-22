package com.jordirubiralta.feature.users

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordirubiralta.domain.usecase.DeleteUserUseCase
import com.jordirubiralta.domain.usecase.FetchMoreUsersUseCase
import com.jordirubiralta.domain.usecase.FetchUsersUseCase
import com.jordirubiralta.feature.users.mapper.UserUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val fetchMoreUsersUseCase: FetchMoreUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<UsersScreenState> =
        MutableStateFlow(UsersScreenState.Loading)
    val state: StateFlow<UsersScreenState> = _state.asStateFlow()

    private val _effect = Channel<UserScreenUIEffect>()
    val effect: Flow<UserScreenUIEffect> = _effect.receiveAsFlow()

    private var page: Int? = null

    fun fetchUsers(search: String? = null) {
        viewModelScope.launch {
            try {
                val userListModel = fetchUsersUseCase(search = search)
                page = userListModel.page
                _state.value = if (userListModel.userList.isNotEmpty()) {
                    UsersScreenState.Success(
                        isLoadingMore = false,
                        showLoadMore = search.isNullOrBlank(),
                        userList = UserUIMapper.fromUserModelListToUIModel(
                            list = userListModel.userList
                        )
                    )
                } else {
                    UsersScreenState.Empty
                }
            } catch (e: Exception) {
                _state.value = UsersScreenState.Error(message = e.message)
            }
        }
    }

    fun fetchMoreUsers() {
        viewModelScope.launch {
            _state.value = (state.value as UsersScreenState.Success).copy(
                isLoadingMore = true,
                showLoadMore = false
            )
            try {
                val userListModel = fetchMoreUsersUseCase(page = page?.inc() ?: 1)
                page = userListModel.page

                val currentList =
                    (_state.value as? UsersScreenState.Success)?.userList ?: emptyList()
                val newList = UserUIMapper.fromUserModelListToUIModel(list = userListModel.userList)

                _state.value = UsersScreenState.Success(
                    (currentList + newList).toImmutableList(),
                    isLoadingMore = false,
                    showLoadMore = true
                )
            } catch (e: Exception) {
                _state.value = (state.value as UsersScreenState.Success).copy(
                    isLoadingMore = false,
                    errorMessage = e.message
                )

            }
        }
    }

    fun deleteUser(email: String, context: Context) {
        viewModelScope.launch {
            deleteUserUseCase(email)

            val currentList = (_state.value as UsersScreenState.Success).userList
            val newList = currentList.filterNot { it.email == email }.toImmutableList()
            _state.value = if (newList.isEmpty()) {
                UsersScreenState.Empty
            } else {
                UsersScreenState.Success(newList)
            }

            _effect.send(
                UserScreenUIEffect.DeleteItemSnackbar(
                    message = context.getString(R.string.snackbar_delete_item)
                )
            )
        }
    }
}
