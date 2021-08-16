package com.example.amateriapp.data.network

import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.data.model.AlbumList
import retrofit2.Call
import retrofit2.http.*

/** Retrofit interface for login and album detail api calls */
interface AlbumApi {

    @Headers("X-App-ID: A3357xuZjV", "Authorization: Basic ZGV2OmRldmRldg==", "Accept: application/json")
    @GET("albums/extended")
    fun getAlbumInfo(@Header("X-Token") token: String,
                     @Query("limit") limit: Int,
                     @Query("offset") offset: Int,
    @Query("sort") sort:String): Call<AlbumList>



    @Headers("X-App-ID: A3357xuZjV", "Authorization: Basic ZGV2OmRldmRldg==", "Accept: application/json")
    @GET("album/{id}/extended")
    fun getAlbumDetail(@Header("X-Token") token: String,
                       @Path("id") albumId: Int): Call<AlbumDetail>
    
}