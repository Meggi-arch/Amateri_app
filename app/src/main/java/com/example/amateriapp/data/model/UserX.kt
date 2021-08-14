package com.example.amateriapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserX(

    var id:Int,
    val nick: String,
    val online: Boolean,
    val sex: String,
    val avatar_url: String?
)