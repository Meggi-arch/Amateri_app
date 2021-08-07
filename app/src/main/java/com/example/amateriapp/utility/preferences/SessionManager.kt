package com.example.amateriapp.utility.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.amateriapp.R
import com.example.amateriapp.utility.Constant.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Session manager to save and fetch data from SharedPreferences
 */
class SessionManager @Inject constructor(private val preferences: SharedPreferences) {



    /**
     * Function to save auth token
     */
     fun setToken(token: String) {
        val editor = preferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */

     fun getToken() = preferences.getString(USER_TOKEN, "null").toString()





}