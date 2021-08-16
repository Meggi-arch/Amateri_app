package com.example.amateriapp.utility


import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.data.model.User
import com.example.amateriapp.utility.preferences.SessionManager

interface AlbumDetailActivityContract {

        /**
         * Call when user interact with the view
         * */
        interface Presenter {
             fun requestDataFromServer()
        }

        /**
         * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
         * while the setDataToRecyclerView, setData and onResponseFailure is fetched from the ApiListener class
         */
        interface MainView {
            fun showProgress()
            fun hideProgress()
            fun onResponseFailure(message: String)
            fun setDataToRecyclerView(data: AlbumDetail?)
            fun setData(data: AlbumDetail?)
        }

        /**
         * Intractors are classes built for fetching data from your database, web services, or any other data source.
         */

        interface ApiListener {
            interface OnFinishedListener {
                fun onFinished(data: AlbumDetail?)
                fun onFailure(t: String)
            }

             fun getAlbumDetail(onFinishedListener: OnFinishedListener?, id:Int)
        }

}