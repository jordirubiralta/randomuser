package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.model.UserModel
import com.jordirubiralta.domain.repository.UserRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(search: String?): List<UserModel> =
        userRepository.getUsers(search = search)
}
