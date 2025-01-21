package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.repository.UserRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FetchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(search: String?): UserListModel {
        val userListModel = userRepository.fetchUsers()

        return search?.takeUnless { it.isBlank() }?.let {
            userListModel.filterList(it)
        } ?: userListModel
    }
}
