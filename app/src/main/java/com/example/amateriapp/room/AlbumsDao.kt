package com.example.amateriapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.amateriapp.data.model.UserX

/** Room database Dao for albums */
@Dao
interface AlbumsDao {

    //Inserts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(user: UserX)



    //User queries
    @Query("SELECT * FROM users WHERE id=:id")
     fun getUser(id: Int): UserX?



}