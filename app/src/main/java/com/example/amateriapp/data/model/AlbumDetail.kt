package com.example.amateriapp.data.model

data class AlbumDetail(val album: Album,
val users: List<UserX>,
                       val images: List<Image>,
                       val comments: List<Comment>)