package com.example.amateriapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.amateriapp.data.model.UserX
import com.example.amateriapp.room.AlbumsDao

/** Room main database  */
@Database(entities = [UserX::class], version = 15, exportSchema = false)
abstract class AmaterDatabase: RoomDatabase(){

    //Dao getters
    abstract fun AlbumsDao(): AlbumsDao

}