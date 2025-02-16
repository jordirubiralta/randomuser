package com.jordirubiralta.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val title: String?,
    val name: String,
    val surname: String,
    val gender: String?,
    val thumbnailImageUrl: String?,
    val largeImageUrl: String?,
    val phone: String?,
    val registeredDate: String?,
    val location: String?
)
