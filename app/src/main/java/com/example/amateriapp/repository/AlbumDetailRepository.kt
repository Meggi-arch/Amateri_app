package com.example.amateriapp.repository

import android.util.Log
import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.utility.AlbumDetailActivityContract
import com.example.amateriapp.utility.Constant.TAG
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import com.example.amateriapp.data.network.AlbumApi

/** Repository for album detail*/
@ActivityScoped
class AlbumDetailRepository @Inject constructor(
    private var api: AlbumApi,
    private var sessionManager: SessionManager
) : AlbumDetailActivityContract.ApiListener {

    /** Gets album detail */
    override fun getAlbumDetail(
        onFinishedListener: AlbumDetailActivityContract.ApiListener.OnFinishedListener?,
        id: Int
    ) {

        val call = api.getAlbumDetail(
            sessionManager.getToken(),
            id
        )

        call.enqueue(object : Callback<AlbumDetail> {
            override fun onResponse(call: Call<AlbumDetail>, response: Response<AlbumDetail>) {

                if (response.isSuccessful && response.body() != null) {

                    if (response.code() == 200) {


                        val responseFromAPI: AlbumDetail? = response.body()

                        Log.d(TAG, id.toString())

                        Log.d(TAG, "Response code album " + response.code().toString())


                        onFinishedListener?.onFinished(responseFromAPI)

                    } else {
                        Log.d(TAG, "Response code album " + response.code().toString())
                        onFinishedListener!!.onFailure("Error")
                    }
                } else {
                    Log.d(TAG, "Response code album " + response.code().toString())
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


