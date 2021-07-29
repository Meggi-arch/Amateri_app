package com.example.amateriapp.data.model

data class Image(
    val album_id: Int,
    val descr: String,
    val exif: Exif,
    val height: Int,
    val id: Int,
    val sort: Int,
    val url: String,
    val width: Int
)