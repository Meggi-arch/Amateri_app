package com.example.amateriapp.presenter


import com.example.amateriapp.data.model.Login
import com.example.amateriapp.data.model.User
import com.example.amateriapp.utility.LoginActivityContract
import com.example.amateriapp.utility.preferences.SessionManager
import javax.inject.Inject

class LoginActivityPresenter @Inject constructor(private var mainView: LoginActivityContract.MainView,
                                                 private var getNoticeIntractor: LoginActivityContract.ApiListener,
                                                 private val login: Login,
                                                 private var sessionManager: SessionManager
): LoginActivityContract.Presenter, LoginActivityContract.ApiListener.OnFinishedListener {



    //Introduce the data obtained by the model layer to the view layer
    override fun requestDataFromServer() {

        getNoticeIntractor.getNoticeList(this,login,sessionManager)

    }

    override fun onFinished(s: User) {
        mainView.onResponseSucces()
        mainView.hideProgress()
    }

    override fun onFailure(t: String) {
        mainView.onResponseFailure(t)
        mainView.hideProgress()
    }

    override fun onResponseFailure() {
        mainView.hideProgress()
    }


}

