package com.example.amateriapp.repository

import android.util.Log
import com.example.amateriapp.data.model.AlbumList
import com.example.amateriapp.data.network.AlbumApi
import com.example.amateriapp.utility.AlbumActivityContract
import com.example.amateriapp.utility.AlbumSort
import com.example.amateriapp.utility.Constant.TAG
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class AlbumRepository @Inject constructor(private var api: AlbumApi) : AlbumActivityContract.ApiListener {


    override fun getNoticeList(
        onFinishedListener: AlbumActivityContract.ApiListener.OnFinishedListener?,
        sessionManager: SessionManager,
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

