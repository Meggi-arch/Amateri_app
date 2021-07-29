package com.example.amateriapp.data.model

data class User(
    val isAdmin: Boolean,
    val token: String,
    val user: UserX
)