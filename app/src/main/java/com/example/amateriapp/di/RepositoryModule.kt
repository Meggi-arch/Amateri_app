package com.example.amateriapp.di

import com.example.amateriapp.data.network.AlbumApi
import com.example.amateriapp.data.network.LoginApi
import com.example.amateriapp.repository.AlbumDetailRepository
import com.example.amateriapp.repository.AlbumRepository
import com.example.amateriapp.repository.LoginRepository
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
  //  @Named("albumDetail")
    fun provideAlbumDetailRepository(
        api: AlbumApi
    ) = AlbumDetailRepository(api)

    /** Provides login repository */
    @Singleton
    @Provides
    @Named("login")
    fun provideLoginRepository(
        api: LoginApi
    ) = LoginRepository(api)

    /** Provides album repository */
    @Singleton
    @Provides
 //   @Named("album")
    fun provideAlbumRepository(
        api: AlbumApi
    ) = AlbumRepository(api)
}