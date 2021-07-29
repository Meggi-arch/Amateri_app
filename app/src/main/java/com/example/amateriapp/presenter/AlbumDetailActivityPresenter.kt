package com.example.amateriapp.presenter

import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.utility.AlbumDetailActivityContract
import com.example.amateriapp.utility.SessionManager

class AlbumDetailActivityPresenter(
    private var mainView: AlbumDetailActivityContract.MainView,
    private var getNoticeIntractor: AlbumDetailActivityContract.ApiListener,
    private var sessionManager: SessionManager,
    private val id: Int
): AlbumDetailActivityContract.Presenter, AlbumDetailActivityContract.ApiListener.OnFinishedListener {

    override fun requestDataFromServer() {
        getNoticeIntractor.getNoticeList(this, sessionManager,id)
    }

    override fun onFinished(data: AlbumDetail?) {
        mainView.setDataToRecyclerView(data)
        mainView.setData(data)
        mainView.hideProgress()
    }


    override fun onFailure(t: String) {
        mainView.onResponseFailure(t)
        mainView.hideProgress()
    }
}