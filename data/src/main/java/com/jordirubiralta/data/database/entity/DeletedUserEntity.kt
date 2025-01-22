package com.jordirubiralta.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_users")
data class DeletedUserEntity(
    @PrimaryKey val email: String,
)
