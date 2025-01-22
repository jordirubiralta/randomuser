package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.repository.UserRepository
import javax.inject.Inject

class FetchMoreUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /**
     * Fetches a page of users from the network.
     *
     * This function invokes the `fetchUsersFromNetwork` method of the user repository
     * to fetch users for the specified page. The result is a `UserListModel` containing
     * the users fetched from the network.
     *
     * @param page The page number to fetch users for.
     * @return A `UserListModel` containing the list of users for the specified page.
     */
    suspend operator fun invoke(page: Int): UserListModel =
        userRepository.fetchUsersFromNetwork(page = page)
}
