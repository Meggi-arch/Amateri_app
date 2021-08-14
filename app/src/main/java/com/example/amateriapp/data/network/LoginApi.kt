package com.example.amateriapp.data.network

import com.example.amateriapp.data.model.Login
import com.example.amateriapp.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/** Retrofit interface for login api calls  */
interface LoginApi {

    @POST("users/login")
    fun createAmaterApi(@Header("Accept") accept: String,
                        @Header("X-App-ID") xapp: String,
                        @Header("Authorization") authorization: String,
                        @Header("User-Agent") user_agent: String,
                        @Header("Content-Type") content_type: String,
                        @Body login: Login): Call<User>
}