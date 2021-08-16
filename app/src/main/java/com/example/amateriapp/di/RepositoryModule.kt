package com.example.amateriapp.di

import com.example.amateriapp.data.network.AlbumApi
import com.example.amateriapp.data.network.LoginApi
import com.example.amateriapp.repository.AlbumDetailRepository
import com.example.amateriapp.repository.AlbumRepository
import com.example.amateriapp.repository.LoginRepository
import com.example.amateriapp.room.AlbumsDao
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/** Dependency injection module to provide repositories */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /** Provides album detail repository */
    @Singleton
    @Provides
    fun provideAlbumDetailRepository(
        api: AlbumApi,
        sessionManager: SessionManager
    ) = AlbumDetailRepository(api,sessionManager)

    /** Provides login repository */
    @Singleton
    @Provides
    fun provideLoginRepository(
        api: LoginApi,
sessionManager: SessionManager
    ) = LoginRepository(api,sessionManager)

    /** Provides album repository */
    @Singleton
    @Provides
    fun provideAlbumRepository(
        api: AlbumApi,
        sessionManager: SessionManager,
        albumsDao: AlbumsDao,
    ) = AlbumRepository(api,sessionManager,albumsDao)
}