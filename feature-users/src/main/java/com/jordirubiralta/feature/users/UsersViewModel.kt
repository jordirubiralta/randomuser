package com.jordirubiralta.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordirubiralta.domain.usecase.DeleteUserUseCase
import com.jordirubiralta.domain.usecase.GetUserListUseCase
import com.jordirubiralta.feature.users.mapper.UserUIMapper
import com.jordirubiralta.feature.users.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UsersScreenState(isLoading = true))
    val state: StateFlow<UsersScreenState> = _state.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            val users = getUserListUseCase.invoke()
            reduceState(
                isLoading = false,
                userList = UserUIMapper.fromUserModelListToUIModel(users)
            )
        }
    }

    fun deleteUser(email: String) {
        viewModelScope.launch {
            deleteUserUseCase.invoke(email)
            val newList = _state.value.userList.filterNot { it.email == email }.toImmutableList()
            reduceState(userList = newList)
            // TODO show deleted snackbar
        }
    }

    private fun reduceState(
        isLoading: Boolean? = null,
        isLoadingMore: Boolean? = null,
        userList: ImmutableList<UserUIModel>? = null
    ) {
        _state.update {
            it.copy(
                isLoading = isLoading ?: _state.value.isLoading,
                isLoadingMore = isLoadingMore ?: _state.value.isLoadingMore,
                userList = userList ?: _state.value.userList
            )
        }

    }
}
