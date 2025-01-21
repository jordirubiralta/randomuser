package com.jordirubiralta.feature.users.mapper

import com.jordirubiralta.domain.model.UserModel
import com.jordirubiralta.feature.users.model.UserUIModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

object UserUIMapper {

    fun fromUserModelListToUIModel(list: List<UserModel>): ImmutableList<UserUIModel> =
        list.map(::fromUserModelToUIModel).toImmutableList()

    private fun fromUserModelToUIModel(model: UserModel) = UserUIModel(
        name = model.name,
        surname = model.surname,
        email = model.email,
        imageUrl = model.imageUrl,
        phone = model.phone
    )

}
