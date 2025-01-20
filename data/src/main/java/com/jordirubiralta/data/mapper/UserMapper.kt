package com.jordirubiralta.data.mapper

import com.jordirubiralta.data.model.UserListResponse
import com.jordirubiralta.data.model.UserResponse
import com.jordirubiralta.domain.model.UserModel

object UserMapper {

    fun fromUserListResponseToModel(response: UserListResponse): List<UserModel> =
        response.results?.map(::fromUserResponseToModel) ?: emptyList()

    private fun fromUserResponseToModel(response: UserResponse) = UserModel(
        gender = response.gender,
        title = "response.name?.title",
        name = "response.name?.first.orEmpty()",
        surname = "response.name?.last.orEmpty()",
        email = "response.email.orEmpty()",
        phone = "response.phone",
        imageUrl = "response.picture?.thumbnail"
        /*title = response.name?.title,
        name = response.name?.first.orEmpty(),
        surname = response.name?.last.orEmpty(),
        email = response.email.orEmpty(),
        phone = response.phone,
        imageUrl = response.picture?.thumbnail*/
    )

}
