package com.example.amateriapp.data.network

import com.example.amateriapp.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface AmaterApi {


    @POST("users/login")
     fun createAmaterApi(@Header("Accept") accept: String,
                      @Header("X-App-ID") xapp: String,
                      @Header("Authorization") authorization: String,
                      @Header("User-Agent") user_agent: String,
                      @Header("Content-Type") content_type: String,
                      @Body login: Login): Call<User>


     @GET("albums/extended")
    fun getAlbumInfo(@Header("Accept") accept: String,
                      @Header("User-Agent") user_agent: String,
                      @Header("Authorization") authorization: String,
                      @Header("X-App-ID") xapp: String,
                     @Header("Content-Type") content_type: String,
                     @Header("X-Token") token: String,
                     @Query("limit") limit: Int,
                     @Query("offset") offset: Int): Call<AlbumList>


    @GET("album/{id}/extended")
    fun getAlbumDetail(@Header("Accept") accept: String,
                     @Header("User-Agent") user_agent: String,
                     @Header("Authorization") authorization: String,
                     @Header("X-App-ID") xapp: String,
                     @Header("Content-Type") content_type: String,
                     @Header("X-Token") token: String,
                       @Path("id") albumId: Int,
                     @Query("comments") comm: Int): Call<AlbumDetail>

}