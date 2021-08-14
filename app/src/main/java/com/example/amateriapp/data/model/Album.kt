package com.example.amateriapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Model data class represents album of images
 *  Also as entity for DB */
data class Album(
    val id: Int,
    val category: String,
    val category_id: Int,
    val commentAllowed: Boolean,
    val comments_count: Int,
    val count_view: Int,
    val description: String,
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