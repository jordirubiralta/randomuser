package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    /**
     * Fetches a user by their email.
     *
     * This function retrieves a user from the repository using the provided email.
     * If the user exists, it returns the corresponding user. If no user is found for the provided
     * email, it may return `null`.
     *
     * @param email The email of the user to be retrieved.
     * @return The user associated with the provided email.
     */
    suspend operator fun invoke(email: String) =
        userRepository.getUserByEmail(email = email)

}
