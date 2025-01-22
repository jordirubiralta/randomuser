package com.jordirubiralta.data

import com.jordirubiralta.data.database.entity.UserEntity
import com.jordirubiralta.data.model.InfoResponse
import com.jordirubiralta.data.model.LocationResponse
import com.jordirubiralta.data.model.NameResponse
import com.jordirubiralta.data.model.PictureResponse
import com.jordirubiralta.data.model.RegisteredResponse
import com.jordirubiralta.data.model.StreetResponse
import com.jordirubiralta.data.model.UserListResponse
import com.jordirubiralta.data.model.UserResponse
import com.jordirubiralta.domain.model.UserListModel
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

    val DuplicatedUserModelList = listOf(
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
        )
    )

    val UserListResponse = UserListResponse(
        results = listOf(
            UserResponse(
                gender = "male",
                name = NameResponse(
                    title = "Mr",
                    first = "John",
                    last = "Smith"
                ),
                location = LocationResponse(
                    street = StreetResponse(name = "Oxford Street", number = 2),
                    city = "London",
                    state = "London",
                    country = "United Kingdom"
                ),
                email = "john.smith@example.com",
                registered = RegisteredResponse(
                    date = "05-23-2020",
                    age = 4
                ),
                phone = "666666666",
                picture = PictureResponse(
                    medium = "www.medium.com",
                    large = "www.large.com",
                    thumbnail = "www.thumbnail.com"
                )
            )
        ),
        info = InfoResponse(
            results = 20,
            page = 1
        )
    )

    val EmptyUserListResponse = UserListResponse(
        results = listOf(
            UserResponse(
                gender = null,
                name = null,
                location = null,
                email = null,
                registered = null,
                phone = null,
                picture = null
            )
        ),
        info = null
    )

    val UserListModel = UserListModel(
        userList = UserModelList,
        page = 2
    )

    val DuplicatedUserListModel = UserListModel(
        userList = DuplicatedUserModelList,
        page = 2
    )
}
