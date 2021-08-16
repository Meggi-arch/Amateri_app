package com.example.amateriapp.presenter

import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.data.model.User
import com.example.amateriapp.utility.AlbumDetailActivityContract
import com.example.amateriapp.utility.preferences.SessionManager
import javax.inject.Inject

class AlbumDetailActivityPresenter @Inject constructor(
    private var mainView: AlbumDetailActivityContract.MainView,
    private var getNoticeIntractor: AlbumDetailActivityContract.ApiListener,
    private val id: Int
): AlbumDetailActivityContract.Presenter, AlbumDetailActivityContract.ApiListener.OnFinishedListener {

    /**
     * Introduce the data obtained by the model layer to the view layer
     * */

    override fun requestDataFromServer() {
        getNoticeIntractor.getAlbumDetail(this,id)

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