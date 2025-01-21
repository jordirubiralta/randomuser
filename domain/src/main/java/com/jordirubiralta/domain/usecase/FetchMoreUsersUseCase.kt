package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.repository.UserRepository
import javax.inject.Inject

class FetchMoreUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(search: String?, page: Int): UserListModel =
        userRepository.fetchMoreUsers(search = search, page = page)
}
