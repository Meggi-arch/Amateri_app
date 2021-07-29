package com.example.amateriapp.data.model

data class Comment(
    val id: Int,
    val item_id: Int,
    val lang: String,
    val message: String,
    val time: String,
    val user_id: Int
)