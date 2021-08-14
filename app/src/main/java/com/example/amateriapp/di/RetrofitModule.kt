package com.example.amateriapp.di

import com.example.amateriapp.data.network.AlbumApi
import com.example.amateriapp.data.network.LoginApi
import com.example.amateriapp.utility.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/** Dependency injection module to provide retrofit interfaces */
@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {


    /** Provides album retrofit with Gson and base url for api calls */
    @Singleton
    @Provides
    @Named("album")
    fun provideAlbumApi(): AlbumApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL_ALBUM)
            .build()
            .create(AlbumApi::class.java)
    }

    /** Provides login retrofit with Gson and base url for api calls */
    @Singleton
    @Provides
    @Named("login")
    fun provideLoginApi(): LoginApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build()
            .create(LoginApi::class.java)
    }


    /** Provides album detail retrofit with Gson and base url for api calls */
    @Singleton
    @Provides
    @Named("albumDetail")
    fun provideAlbumDetailApi(): AlbumApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL_ALBUM)
            .build()
            .create(AlbumApi::class.java)
    }

}