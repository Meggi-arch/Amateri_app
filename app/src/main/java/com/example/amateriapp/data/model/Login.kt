package com.example.amateriapp.data.model

/** Data class for login request
 * Used as body for api call */
data class Login(
    val nick: String,
    val password: String
)