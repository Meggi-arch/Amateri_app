package com.example.amateriapp.utility

import com.example.amateriapp.data.model.Login
import com.example.amateriapp.data.model.User
import com.example.amateriapp.utility.preferences.SessionManager

interface LoginActivityContract {


        /**
         * Call when user interact with the view
         * */
        interface Presenter {
            fun requestDataFromServer()
        }

        /**
         * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
         * while the onResponseSucces and onResponseFailure is fetched from the ApiListener class
         */
        interface MainView {
            fun showProgress()
            fun hideProgress()
            fun onResponseSucces()
            fun onResponseFailure(message: String)

        }

        /**
         * Intractors are classes built for fetching data from your database, web services, or any other data source.
         */
        interface ApiListener {
            interface OnFinishedListener {
                 fun onFinished(s: User)
                 fun onFailure(t: String)
                fun onResponseFailure()
            }

             fun getNoticeList(onFinishedListener: OnFinishedListener?, login: Login, sessionManager: SessionManager)
        }


}