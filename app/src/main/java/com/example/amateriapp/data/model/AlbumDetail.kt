package com.example.amateriapp.data.model

/** Data class for album detail api call response */
data class AlbumDetail(

    val album: Album,
    val users: List<UserX>,
    val images: List<Image>,
    val comments: List<Comment>

    )