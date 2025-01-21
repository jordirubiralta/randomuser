package com.jordirubiralta.feature.users

import com.jordirubiralta.feature.users.model.UserUIModel
import kotlinx.collections.immutable.ImmutableList

sealed class UsersScreenState {
    data object Loading : UsersScreenState()
    data object Empty : UsersScreenState()
    data class Success(
        val userList: ImmutableList<UserUIModel>,
        val isLoadingMore: Boolean = false,
        val showLoadMore: Boolean = true,
        val errorMessage: String? = null,
    ) : UsersScreenState()

    data class Error(val message: String?) : UsersScreenState()
}
