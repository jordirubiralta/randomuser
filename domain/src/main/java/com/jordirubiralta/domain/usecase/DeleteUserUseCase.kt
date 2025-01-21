package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String) {
        userRepository.deleteUser(email)
    }
}
