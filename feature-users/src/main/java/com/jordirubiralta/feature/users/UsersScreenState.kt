package com.jordirubiralta.feature.users

import com.jordirubiralta.feature.users.model.UserUIModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class UsersScreenState(
    val isLoading: Boolean = false,
    val userList: ImmutableList<UserUIModel> = emptyList<UserUIModel>().toImmutableList()
)
