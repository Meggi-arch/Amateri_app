package com.example.amateriapp.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.amateriapp.R
import com.example.amateriapp.data.model.Login
import com.example.amateriapp.data.network.AmaterApi
import com.example.amateriapp.databinding.ActivityLoginBinding
import com.example.amateriapp.presenter.LoginActivityPresenter
import com.example.amateriapp.repository.LoginRepository
import com.example.amateriapp.utility.LoginActivityContract
import com.example.amateriapp.utility.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginActivityContract.MainView {

    @Inject
    @Named("login")
    lateinit var api: AmaterApi

    @JvmField
    @Inject
    var internet: Boolean = false

    private var presenter: LoginActivityContract.Presenter? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val  sessionManager = SessionManager(this)

        // Check if the user is already logged in
        val token = sessionManager.fetchAuthToken()
        if(token != "null"){

    startActivity(Intent(this, AlbumActivity::class.java))
finish()
    }


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

            lifecycleScope.launchWhenCreated {
                presenter!!.requestDataFromServer()
            }

        }


    }


    override fun showProgress() {
       binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

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