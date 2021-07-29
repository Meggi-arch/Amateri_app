package com.example.amateriapp.repository

import android.util.Log
import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.data.network.AmaterApi
import com.example.amateriapp.utility.AlbumDetailActivityContract
import com.example.amateriapp.utility.Constant.TAG
import com.example.amateriapp.utility.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumDetailRepository(private var api: AmaterApi) : AlbumDetailActivityContract.ApiListener {

    override fun getNoticeList(
        onFinishedListener: AlbumDetailActivityContract.ApiListener.OnFinishedListener?,
        sessionManager: SessionManager,
        id: Int
    ) {

        val call = api.getAlbumDetail( "application/json",
            "Amateri/cURL",
            "Basic ZGV2OmRldmRldg==",
            "A3357xuZjV",
            "application/json",
            "${sessionManager.fetchAuthToken()}",
            id,
            1)

        call.enqueue(object: Callback<AlbumDetail> {
            override fun onResponse(call: Call<AlbumDetail>, response: Response<AlbumDetail>) {

                if(response.isSuccessful && response.body() != null) {

                    if (response.code() == 200){


                        val responseFromAPI: AlbumDetail? = response.body()

                        Log.d(TAG, id.toString())

                        Log.d(TAG,"Response code album " +response.code().toString())


                        onFinishedListener?.onFinished(responseFromAPI)

                    }else{
                        Log.d(TAG,"Response code album " + response.code().toString())
                        onFinishedListener!!.onFailure("Error")
                    }
                }else{
                    Log.d(TAG,"Response code album " + response.code().toString())
                    onFinishedListener!!.onFailure("Error")
                }

            }

            override fun onFailure(call: Call<AlbumDetail>, t: Throwable) {

                Log.d(TAG, t.message.toString())
                onFinishedListener?.onFailure(t.message.toString())
            }

        })
    }
}