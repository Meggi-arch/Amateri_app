package com.example.amateriapp.utility

import com.example.amateriapp.data.model.Album
import com.example.amateriapp.utility.preferences.SessionManager

interface AlbumActivityContract {
    /**
     * Call when user interact with the view
     * */
    interface Presenter {
          fun requestDataFromServer()
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the ApiListener class
     */
    interface MainView {
        fun showProgress()
        fun hideProgress()
         fun setDataToRecyclerView(noticeArrayList: List<Album>)
        fun onResponseFailure(message: String)
    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     */

    interface ApiListener {
        interface OnFinishedListener {
             fun onFinished(data: List<Album>)
             fun onFailure(t: String)
        }

         fun getNoticeList(onFinishedListener: OnFinishedListener?, sessionManager: SessionManager,albumSort: AlbumSort)
    }
}