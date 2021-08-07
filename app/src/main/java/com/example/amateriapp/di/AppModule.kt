package com.example.amateriapp.di

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.amateriapp.R
import com.example.amateriapp.data.network.AmaterApi
import com.example.amateriapp.repository.AlbumDetailRepository
import com.example.amateriapp.repository.AlbumRepository
import com.example.amateriapp.repository.LoginRepository
import com.example.amateriapp.utility.Constant.BASE_URL
import com.example.amateriapp.utility.Constant.BASE_URL_ALBUM
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{


    @Singleton
    @Provides
    @Named("album")
    fun provideAlbumRepository(
        api: AmaterApi
    ) = AlbumRepository(api)

    @Singleton
    @Provides
    @Named("album")
    fun provideAlbumApi(): AmaterApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_ALBUM)
            .build()
            .create(AmaterApi::class.java)
    }

    @Singleton
    @Provides
    @Named("login")
    fun provideLoginRepository(
        api: AmaterApi
    ) = LoginRepository(api)

    @Singleton
    @Provides
    @Named("login")
    fun provideLoginApi(): AmaterApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(AmaterApi::class.java)
    }

    @Singleton
    @Provides
    @Named("albumDetail")
    fun provideAlbumDetailRepository(
        api: AmaterApi
    ) = AlbumDetailRepository(api)

    @Singleton
    @Provides
    @Named("albumDetail")
    fun provideAlbumDetailApi(): AmaterApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_ALBUM)
            .build()
            .create(AmaterApi::class.java)
    }

    @Provides
    fun checkinternetConnection(@ApplicationContext context: Context): Boolean{
        val connected: Boolean

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED

        return connected
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(
            context.getString(R.string.app_name), Context.MODE_PRIVATE
        )


    @Singleton
    @Provides
    fun provideSessionManager(preferences: SharedPreferences) =
        SessionManager(preferences)

}