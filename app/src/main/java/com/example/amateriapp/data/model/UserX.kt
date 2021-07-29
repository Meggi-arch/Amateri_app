package com.example.amateriapp.data.model

data class UserX(
    val avatarUrl: String,
    val categoryId: Int,
    val countryId: String,
    val id: Int,
    val isEroticServicesProvider: Boolean,
    val isPhoneVerified: Boolean,
    val isVerified: Boolean,
    val isVip: Boolean,
    val lastLoginAt: String,
    val nick: String,
    val online: Boolean,
    val regionId: String,
    val registeredAt: String,
    val stats: Stats
)