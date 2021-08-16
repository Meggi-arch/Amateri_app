package com.example.amateriapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserX(

    @PrimaryKey
    var id:Int,
    val nick: String,
    val online: Boolean,
    val sex: String,
    val avatar_url: String?
)