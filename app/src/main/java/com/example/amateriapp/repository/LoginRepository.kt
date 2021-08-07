package com.example.amateriapp.repository

import android.util.Log
import com.example.amateriapp.data.model.Login
import com.example.amateriapp.data.model.User
import com.example.amateriapp.data.network.AmaterApi
import com.example.amateriapp.utility.Constant.TAG
import com.example.amateriapp.utility.LoginActivityContract
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class LoginRepository @Inject constructor(private var api: AmaterApi) : LoginActivityContract.ApiListener{



    override fun getNoticeList(
        onFinishedListener: LoginActivityContract.ApiListener.OnFinishedListener?,
        login: Login,
        sessionManager: SessionManager
    ) {


        val call = api.createAmaterApi("application/json",
            "A3357xuZjV",
            "Basic ZGV2OmRldmRldg==",
            "Amateri/cURL",
            "application/json",
            login)


// on below line we are executing our method.
        call.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>?, response: Response<User?>) {


                if(response.isSuccessful && response.body() != null){

                    if (response.code() == 201){

                        sessionManager.setToken(response.body()!!.token)

                        Log.d(TAG,"Token" + response.body()!!.token)

                        val responseFromAPI: User? = response.body()

                        Log.d(TAG,"Response code " +response.code().toString())


                        responseFromAPI?.let { onFinishedListener?.onFinished(it) }

                    }else{
                        Log.d(TAG,"Response code " + response.code().toString())
                        onFinishedListener!!.onFailure("Error")
                    }

                }else{
                    Log.d(TAG,"Response code " + response.code().toString())
                    onFinishedListener!!.onFailure("Error")
                }


            }

            override fun onFailure(call: Call<User?>?, t: Throwable) {

                Log.d(TAG, t.message.toString())
                onFinishedListener!!.onFailure(t.message.toString())


            }
        })
    }
}