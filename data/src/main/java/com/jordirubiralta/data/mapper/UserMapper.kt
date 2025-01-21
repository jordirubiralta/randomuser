package com.jordirubiralta.data.mapper

import com.jordirubiralta.data.database.entity.DeletedUserEntity
import com.jordirubiralta.data.database.entity.UserEntity
import com.jordirubiralta.data.model.UserListResponse
import com.jordirubiralta.data.model.UserResponse
import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.model.UserModel

object UserMapper {

    fun fromUserListResponseToModel(response: UserListResponse) = UserListModel(
        userList = response.results?.map(::fromUserResponseToModel) ?: emptyList(),
        page = response.info?.page
    )

    private fun fromUserResponseToModel(response: UserResponse) = UserModel(
        gender = response.gender,
        title = response.name?.title,
        name = response.name?.first.orEmpty(),
        surname = response.name?.last.orEmpty(),
        email = response.email.orEmpty(),
        phone = response.phone,
        imageUrl = response.picture?.thumbnail
    )

    // model to entity
    fun fromUserListModelToEntity(list: List<UserModel>): List<UserEntity> =
        list.map(::fromUserModelToEntity)

    fun fromUserModelToEntity(model: UserModel) = UserEntity(
        title = model.title,
        name = model.name,
        surname = model.surname,
        email = model.email,
        imageUrl = model.imageUrl,
        phone = model.phone,
        gender = model.gender
    )

    // entity to model
    fun fromUserListEntityToModel(list: List<UserEntity>): List<UserModel> =
        list.map(::fromUserEntityToModel)

    private fun fromUserEntityToModel(entity: UserEntity) = UserModel(
        title = entity.title,
        name = entity.name,
        surname = entity.surname,
        email = entity.email,
        imageUrl = entity.imageUrl,
        gender = entity.gender,
        phone = entity.phone
    )
}
