package com.example.to_do.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: String,
    val userName: String,
    val userPassword: String,
)
