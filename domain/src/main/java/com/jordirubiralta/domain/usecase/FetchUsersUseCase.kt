package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.repository.UserRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class FetchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /**
     * Fetches a list of users with an optional search filter.
     *
     * This function retrieves the full list of users from the repository. If a non-blank search query
     * is provided, it filters the user list using the `filterList` function. If the search query is null
     * or blank, the full list is returned without filtering.
     *
     * @param search The search query to filter the user list. If null or blank, no filtering is applied.
     * @return A `UserListModel` containing the filtered or unfiltered list of users.
     */
    suspend operator fun invoke(search: String?): UserListModel {
        val userListModel = userRepository.fetchUsers()

        return search?.takeUnless { it.isBlank() }?.let {
            userListModel.filterList(it)
        } ?: userListModel
    }
}
