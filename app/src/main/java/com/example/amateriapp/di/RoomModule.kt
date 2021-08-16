package com.example.amateriapp.di

import android.content.Context
import androidx.room.Room
import com.example.amateriapp.room.AlbumsDao
import com.example.amateriapp.room.AmaterDatabase
import com.example.amateriapp.utility.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Dependency injection module to provide room database and interfaces */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    /** Builds and Provides Main database */
    @Singleton
    @Provides
    fun provideMainDatabase(@ApplicationContext context: Context): AmaterDatabase {
        return Room.databaseBuilder(
            context,
            AmaterDatabase::class.java,
           DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    /** Provides Interface to get albums info from DB */
    @Singleton
    @Provides
    fun provideAlbumsDao(mainDatabase: AmaterDatabase): AlbumsDao {
        return mainDatabase.AlbumsDao()
    }
}