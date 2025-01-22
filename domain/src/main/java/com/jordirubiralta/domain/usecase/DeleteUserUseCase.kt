package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /**
     * Deletes a user by their email.
     *
     * This function invokes the `deleteUser` method of the user repository to remove the corresponding user.
     *
     * @param email The email of the user to be deleted.
     */
    suspend operator fun invoke(email: String) {
        userRepository.deleteUser(email)
    }
}
