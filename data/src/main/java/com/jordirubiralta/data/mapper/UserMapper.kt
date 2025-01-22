package com.jordirubiralta.data.mapper

import com.jordirubiralta.data.database.entity.UserEntity
import com.jordirubiralta.data.model.UserListResponse
import com.jordirubiralta.data.model.UserResponse
import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.model.UserModel

object UserMapper {

    // response to model
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
        thumbnailImageUrl = response.picture?.thumbnail,
        largeImageUrl = response.picture?.large,
        location = response.location?.toString(),
        registreredDate = response.registered?.date
    )

    // model to entity
    fun fromUserListModelToEntity(list: List<UserModel>): List<UserEntity> =
        list.map(::fromUserModelToEntity)

    fun fromUserModelToEntity(model: UserModel) = UserEntity(
        gender = model.gender,
        title = model.title,
        name = model.name,
        surname = model.surname,
        email = model.email,
        phone = model.phone,
        thumbnailImageUrl = model.thumbnailImageUrl,
        largeImageUrl = model.largeImageUrl,
        location = model.location,
        registreredDate = model.registreredDate
    )

    fun fromUserListEntityToModel(list: List<UserEntity>): List<UserModel> =
        list.map(::fromUserEntityToModel)

    fun fromUserEntityToModel(entity: UserEntity) = UserModel(
        gender = entity.gender,
        title = entity.title,
        name = entity.name,
        surname = entity.surname,
        email = entity.email,
        phone = entity.phone,
        thumbnailImageUrl = entity.thumbnailImageUrl,
        largeImageUrl = entity.largeImageUrl,
        location = entity.location,
        registreredDate = entity.registreredDate
    )
}
