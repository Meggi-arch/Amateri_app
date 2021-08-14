package com.example.amateriapp.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.amateriapp.R
import com.example.amateriapp.data.model.Login
import com.example.amateriapp.data.network.LoginApi
import com.example.amateriapp.databinding.ActivityLoginBinding
import com.example.amateriapp.presenter.LoginActivityPresenter
import com.example.amateriapp.repository.LoginRepository
import com.example.amateriapp.utility.LoginActivityContract
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

/** Activity to login
 *  Contains login form - gets user info with token
 *  Opens album after successful login */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginActivityContract.MainView {

    @Inject
    @Named("login")
    lateinit var api: LoginApi

    @JvmField
    @Inject
    var internet: Boolean = false

    @Inject
    lateinit var sessionManager: SessionManager

    private var presenter: LoginActivityContract.Presenter? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AmateriApp)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        checkToken()
        setupButtons()



    }


    /** Check if the user is already logged in  */
    private fun checkToken(){
        val token = sessionManager.getToken()
        if(token != "null"){

            startActivity(Intent(this, AlbumActivity::class.java))
            finish()
        }
    }

    /** Setups buttons on click listeners */
    private fun setupButtons(){
        binding.button.setOnClickListener{

            val username = binding.editTextUsernameLogin.text.toString()
            val password = binding.editTextPasswordLogin.text.toString()

            if(internet){
                validateInputs(username,password,sessionManager)
            }else{
                Toast.makeText(this, getString(R.string.interent), Toast.LENGTH_LONG).show()
            }


        }
    }

    /**
     * Validate inputs
     */
    private fun validateInputs(s: String, s1: String,sessionManager: SessionManager) {

        if(s.isNotEmpty() || s1.isNotEmpty()){

            showProgress()

            presenter = LoginActivityPresenter(this, LoginRepository(api),
                Login(s,s1), sessionManager)


                presenter!!.requestDataFromServer()


        }


    }


    override fun showProgress() {
       binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    /** Loads Albums Activity and finish login activity */
    override fun onResponseSucces() {
    startActivity(Intent(this, AlbumActivity::class.java))
        finish()
    }


    override fun onResponseFailure(message: String) {
        Toast.makeText(this,
            getString(R.string.error) + message,
            Toast.LENGTH_LONG).show()
        Log.d(TAG, message)
    }


}