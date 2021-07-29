package com.example.amateriapp.data.model

data class Album(
    val category: String,
    val category_id: Int,
    val commentAllowed: Boolean,
    val comments_count: Int,
    val count_view: Int,
    val description: String,
    val id: Int,
    val images_count: Int,
    val lang: String,
    val statusId: Int,
    val thumbId: Int,
    val thumb_height: Int,
    val thumb_url: String,
    val thumb_width: Int,
    val time: String,
    val title: String,
    val user_id: Int,
    val votes_count: Int,
    val votingEnabled: Boolean
)