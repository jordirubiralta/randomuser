package com.jordirubiralta.data

import com.jordirubiralta.data.database.entity.UserEntity
import com.jordirubiralta.domain.model.UserModel

object Mocks {

    val UserEntityList = listOf(
        UserEntity(
            email = "john.doe@example.com",
            title = "Mr.",
            name = "John",
            surname = "Doe",
            gender = "Male",
            thumbnailImageUrl = "http://example.com/thumbnail.jpg",
            largeImageUrl = "http://example.com/large.jpg",
            phone = "123456789",
            registreredDate = "2023-01-01",
            location = "New York"
        ),
        UserEntity(
            email = "jane.doe@example.com",
            title = null,
            name = "Jane",
            surname = "Doe",
            gender = null,
            thumbnailImageUrl = null,
            largeImageUrl = null,
            phone = null,
            registreredDate = "2022-12-01",
            location = null
        )
    )

    val UserModelList = listOf(
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

}
