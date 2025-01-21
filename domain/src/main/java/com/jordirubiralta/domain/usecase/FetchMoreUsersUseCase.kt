package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.repository.UserRepository
import javax.inject.Inject

class FetchMoreUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(page: Int): UserListModel =
        userRepository.fetchUsersFromNetwork(page = page)
}
