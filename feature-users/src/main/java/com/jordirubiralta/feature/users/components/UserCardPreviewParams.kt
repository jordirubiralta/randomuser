package com.jordirubiralta.feature.users.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jordirubiralta.feature.users.model.UserUIModel

class UserCardPreviewParams : PreviewParameterProvider<UserUIModel> {

    override val values: Sequence<UserUIModel> = sequenceOf(
        UserUIModel(
            name = "John",
            surname = "Doe",
            email = "johndoe@example.com",
            imageUrl = "https://randomuser.me/api/portraits/men/1.jpg",
            phone = "+123456789"
        ),
        UserUIModel(
            name = "Jane",
            surname = "Smith",
            email = "janesmith@example.com",
            imageUrl = null,
            phone = null
        )
    )
}
