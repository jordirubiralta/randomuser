package com.jordirubiralta.feature.users.viewmodel

import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.model.UserModel

object Mocks {

    private val UserModelList = listOf(
        UserModel(
            gender = "Male",
            title = "Mr.",
            name = "John",
            surname = "Doe",
            email = "john.doe@example.com",
            thumbnailImageUrl = "http://example.com/thumbnail.jpg",
            largeImageUrl = "http://example.com/large.jpg",
            phone = "123456789",
            location = "New York",
            registreredDate = "2023-01-01"
        ),
        UserModel(
            gender = null,
            title = null,
            name = "Jane",
            surname = "Doe",
            email = "jane.doe@example.com",
            thumbnailImageUrl = null,
            largeImageUrl = null,
            phone = null,
            location = null,
            registreredDate = "2022-12-01"
        )
    )

    val UserListModel = UserListModel(
        userList = UserModelList,
        page = 2
    )

    val UserModel = UserModel(
        gender = "Male",
        title = "Mr.",
        name = "David",
        surname = "Palmer",
        email = "david.palmer@example.com",
        thumbnailImageUrl = "http://example.com/thumbnail.jpg",
        largeImageUrl = "http://example.com/large.jpg",
        phone = "123456789",
        location = "New York",
        registreredDate = "2023-01-01"
    )
}
