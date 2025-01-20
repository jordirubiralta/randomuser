package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.model.UserModel
import com.jordirubiralta.domain.repository.UserRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    companion object {
        private const val USERS_COUNT = 20
    }

    suspend operator fun invoke(): List<UserModel> =
        userRepository.getUsers(USERS_COUNT)
}
