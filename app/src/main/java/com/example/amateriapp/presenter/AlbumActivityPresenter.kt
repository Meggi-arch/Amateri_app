package com.example.amateriapp.presenter

import com.example.amateriapp.data.model.Album
import com.example.amateriapp.utility.AlbumActivityContract
import com.example.amateriapp.utility.AlbumSort
import com.example.amateriapp.utility.preferences.SessionManager

class AlbumActivityPresenter(
    private var mainView: AlbumActivityContract.MainView,
    private var getNoticeIntractor: AlbumActivityContract.ApiListener,
    private var sessionManager: SessionManager,
    private var albumSort: AlbumSort
): AlbumActivityContract.Presenter, AlbumActivityContract.ApiListener.OnFinishedListener {

    /**
     * Introduce the data obtained by the model layer to the view layer
     * */

    override fun requestDataFromServer() {
        getNoticeIntractor.getNoticeList(this, sessionManager,albumSort)

    }


    override fun onFinished(data: List<Album>) {
        mainView.setDataToRecyclerView(data)
        mainView.hideProgress()

    }


    override fun onFailure(t: String) {
        mainView.onResponseFailure(t)
        mainView.hideProgress()
    }
}