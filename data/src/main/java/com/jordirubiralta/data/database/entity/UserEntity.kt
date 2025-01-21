package com.jordirubiralta.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val name: String,
    val surname: String,
    val email: String,
    val gender: String?,
    val thumbnailImageUrl: String?,
    val largeImageUrl: String?,
    val phone: String?,
    val registreredDate: String?,
    val location: String?
)
