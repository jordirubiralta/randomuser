package com.jordirubiralta.feature.detail

import com.jordirubiralta.domain.model.UserModel

object Mocks {
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
        registeredDate = "2021-12-01T11:06:56.901Z"
    )
}
