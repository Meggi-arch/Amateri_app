package com.example.amateriapp.utility

import com.example.amateriapp.data.model.UserX

/** Callback to load info about user */
interface UserCallback {

    /** Called when item starts loading */
    fun getUserFromRepository(cachedUser: UserX)

}