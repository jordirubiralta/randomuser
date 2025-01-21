package com.jordirubiralta.feature.users

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordirubiralta.domain.usecase.DeleteUserUseCase
import com.jordirubiralta.domain.usecase.FetchMoreUsersUseCase
import com.jordirubiralta.domain.usecase.FetchUsersUseCase
import com.jordirubiralta.feature.users.mapper.UserUIMapper
import com.jordirubiralta.feature.users.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val fetchMoreUsersUseCase: FetchMoreUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UsersScreenState(isLoading = true))
    val state: StateFlow<UsersScreenState> = _state.asStateFlow()

    private val _effect = Channel<UserScreenUIEffect>()
    val effect: Flow<UserScreenUIEffect> = _effect.receiveAsFlow()

    private var page: Int? = null

    fun fetchUsers(search: String? = null) {
        viewModelScope.launch {
            reduceState(isLoading = true)
            val userListModel = fetchUsersUseCase(search = search)
            page = userListModel.page
            reduceState(
                isLoading = false,
                userList = UserUIMapper.fromUserModelListToUIModel(list = userListModel.userList)
            )
        }
    }

    fun fetchMoreUsers() {
        viewModelScope.launch {
            reduceState(isLoading = true)
            val userListModel = fetchMoreUsersUseCase(page = page?.inc() ?: 1)
            page = userListModel.page
            val newList = UserUIMapper.fromUserModelListToUIModel(list = userListModel.userList)
            reduceState(
                isLoading = false,
                userList = (_state.value.userList + newList).toImmutableList()
            )
        }
    }

    fun deleteUser(email: String, context: Context) {
        viewModelScope.launch {
            deleteUserUseCase(email)
            val newList = _state.value.userList.filterNot { it.email == email }.toImmutableList()
            reduceState(userList = newList)
            _effect.send(
                UserScreenUIEffect.DeleteItemSnackbar(
                    message = context.getString(R.string.snackbar_delete_item)
                )
            )
        }
    }

    private fun reduceState(
        isLoading: Boolean? = null,
        userList: ImmutableList<UserUIModel>? = null
    ) {
        _state.update {
            it.copy(
                isLoading = isLoading ?: _state.value.isLoading,
                userList = userList ?: _state.value.userList
            )
        }
    }
}
