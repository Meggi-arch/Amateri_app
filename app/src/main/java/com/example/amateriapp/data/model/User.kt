package com.example.amateriapp.data.model

import androidx.room.Entity


data class User(
    val isAdmin: Boolean,
    val token: String,
    val user: UserX
)