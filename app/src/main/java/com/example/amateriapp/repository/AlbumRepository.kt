package com.example.amateriapp.repository

import android.util.Log
import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.data.model.AlbumList
import com.example.amateriapp.data.model.UserX
import com.example.amateriapp.data.network.AlbumApi
import com.example.amateriapp.room.AlbumsDao
import com.example.amateriapp.utility.AlbumActivityContract
import com.example.amateriapp.utility.AlbumSort
import com.example.amateriapp.utility.Constant.TAG
import com.example.amateriapp.utility.UserCallback
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/** Repository for album and connected classes */
@ActivityScoped
class AlbumRepository @Inject constructor(
    private var api: AlbumApi,
    private var sessionManager: SessionManager,
    private var albumsDao: AlbumsDao
) : AlbumActivityContract.ApiListener {

    /** Gets individual users by id
     * Checks if the user is already stored in the Rom database
     * If so, he'll pull it out. if not, function will save it
     * */
    override fun getUser(
        userId: Int,
        albumId: Int,
        userInfo: UserCallback
    ) {


        //Get user from server by album detail
        val albumDetailResponse = api.getAlbumDetail(sessionManager.getToken(),albumId)

        albumDetailResponse.enqueue(object : Callback<AlbumDetail>{
            override fun onResponse(call: Call<AlbumDetail>, response: Response<AlbumDetail>) {



                Thread {

                var cachedUser =  albumsDao.getUser(userId)
                    if (cachedUser != null)
                        userInfo.getUserFromRepository(cachedUser)
                    else {


                        //Store to DB
                        for (user: UserX in response.body()!!.users)
                            albumsDao.insert(user)


                        //Emit Success
                        cachedUser = albumsDao.getUser(userId)
                        if (cachedUser != null)

                            userInfo.getUserFromRepository(cachedUser)

                        //Do your database´s operations here


                }
                }.start()
            }

            override fun onFailure(call: Call<AlbumDetail>, t: Throwable) {
               Log.d(TAG, "EROR")
            }

        })



    }


    /** Gets all albums */
    override fun getAlbum(
        onFinishedListener: AlbumActivityContract.ApiListener.OnFinishedListener?,
        albumSort: AlbumSort
    ) {


        val call = api.getAlbumInfo(sessionManager.getToken(),
            90,
            0,
                 albumSort.toString())

        // on below line we are executing our method.
        call.enqueue(object: Callback<AlbumList>{
    override fun onResponse(call: Call<AlbumList>, response: Response<AlbumList>) {

        if(response.isSuccessful && response.body() != null) {

            if (response.code() == 200){

                Log.d(TAG,"Response code album " +response.code().toString())


                        onFinishedListener?.onFinished(response.body()!!.albums)

            }else{

                Log.d(TAG,"Response code album " + response.code().toString())
                onFinishedListener!!.onFailure("Error")
            }
        }else{

            Log.d(TAG,"Response code album " + response.code().toString())
            onFinishedListener!!.onFailure("Error")
        }

    }

    override fun onFailure(call: Call<AlbumList>, t: Throwable) {

        Log.d(TAG, t.message.toString())
        onFinishedListener?.onFailure(t.message.toString())
    }

})

    }




}

