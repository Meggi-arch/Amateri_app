package com.example.amateriapp.view

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.amateriapp.R
import com.example.amateriapp.adapter.AlbumAdapter
import com.example.amateriapp.data.model.Album
import com.example.amateriapp.data.network.AlbumApi
import com.example.amateriapp.databinding.ActivityAlbumBinding
import com.example.amateriapp.presenter.AlbumActivityPresenter
import com.example.amateriapp.repository.AlbumRepository
import com.example.amateriapp.room.AlbumsDao
import com.example.amateriapp.utility.AlbumActivityContract
import com.example.amateriapp.utility.AlbumSort
import com.example.amateriapp.utility.Constant.TAG
import com.example.amateriapp.utility.Constant.USER_ID
import com.example.amateriapp.utility.RecyclerItemCallback
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class AlbumActivity : AppCompatActivity(), AlbumActivityContract.MainView {


    @Inject
    @Named("album")
    lateinit var api: AlbumApi

    @Inject
    lateinit var albumDao: AlbumsDao

    @Inject
    @Named("albumDetail")
    lateinit var apiDetail: AlbumApi

    @JvmField
    @Inject
    var internet: Boolean = false

    @Inject
    lateinit var sessionManager: SessionManager


    private var presenter: AlbumActivityContract.Presenter? = null

    private lateinit var binding: ActivityAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AmateriApp)
        setContentView(R.layout.activity_album)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInternet()
        setup()
        initializeRecyclerView()
        setupButtons()
    }

    /** Send data to presenter */
    private fun setup() {
        presenter = AlbumActivityPresenter(this, AlbumRepository(api, sessionManager,albumDao), AlbumSort.COMMENTS)
        (presenter as AlbumActivityPresenter).requestDataFromServer()
    }

    /** Check if user user is connected to network */
    private fun checkInternet(){
        if(!internet)
            Toast.makeText(this, getString(R.string.interent), Toast.LENGTH_LONG).show()

    }

    /** Setups buttons on click listeners */
    private fun setupButtons(){

        // Logout alert dialog
        binding.logout.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_dialog))
                .setPositiveButton(getString(R.string.yes)
                ) { _, _ ->

                    // Logout
                    sessionManager.setToken("null")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()

                }
                .setNegativeButton(getString(R.string.no), null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }



    }
    /**
     *  Initialize RecyclerView
     */
    private fun initializeRecyclerView() {

        val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        binding.gridRecyclerviewAlbum.layoutManager = layoutManager

    }

    /**
     * RecyclerItem click event listener
     */

     val recyclerItemCallback: RecyclerItemCallback =
        object : RecyclerItemCallback {


            override fun onItemClick(notice: Album?) {

                val intent = Intent(this@AlbumActivity, AlbumDetail::class.java)
                intent.putExtra(USER_ID, notice?.id)
               startActivity(intent)
Log.d(TAG, notice?.id.toString())


            }

        }


    override fun showProgress() {
  binding.progressBar2.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar2.visibility = View.GONE
    }


    /** Setups Album list recycler view */
    override fun setDataToRecyclerView(noticeArrayList: List<Album>) {

        runOnUiThread {

            val adapter = AlbumAdapter(recyclerItemCallback,
                AlbumRepository(api, sessionManager, albumDao),
            this)

        adapter.albums = noticeArrayList


        binding.gridRecyclerviewAlbum.adapter = adapter
}


    }

    override fun onResponseFailure(message: String) {
        binding.progressBar2.visibility = View.GONE

        Toast.makeText(this,
            getString(R.string.error) + message,
            Toast.LENGTH_LONG).show()

        Log.d(ContentValues.TAG, message)
    }


}