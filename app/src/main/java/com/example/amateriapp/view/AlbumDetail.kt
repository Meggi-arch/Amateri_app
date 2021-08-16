package com.example.amateriapp.view

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.amateriapp.R
import com.example.amateriapp.adapter.AlbumCommentsAdapter
import com.example.amateriapp.adapter.AlbumFotosAdapter
import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.data.network.AlbumApi
import com.example.amateriapp.databinding.ActivityAlbumDetailBinding
import com.example.amateriapp.presenter.AlbumDetailActivityPresenter
import com.example.amateriapp.repository.AlbumDetailRepository
import com.example.amateriapp.repository.AlbumRepository
import com.example.amateriapp.room.AlbumsDao
import com.example.amateriapp.utility.AlbumDetailActivityContract
import com.example.amateriapp.utility.Constant.USER_ID
import com.example.amateriapp.utility.preferences.SessionManager
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class AlbumDetail : AppCompatActivity(), AlbumDetailActivityContract.MainView {

    @Inject
    @Named("albumDetail")
    lateinit var api: AlbumApi

    @Inject
    lateinit var albumDao: AlbumsDao

    @JvmField
    @Inject
    var internet: Boolean = false

    @Inject
    lateinit var sessionManager: SessionManager

    private var presenter: AlbumDetailActivityContract.Presenter? = null

    private lateinit var binding: ActivityAlbumDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AmateriApp)
        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInternet()
        setup()
        initializeRecyclerViewFotos()
        initializeRecyclerViewComments()
        setupButtons()

    }

    /** Send data to presenter */
    private fun setup() {
        val id = intent.getIntExtra(USER_ID,0)
        presenter = AlbumDetailActivityPresenter(this, AlbumDetailRepository(api, sessionManager),id)
        (presenter as AlbumDetailActivityPresenter).requestDataFromServer()
    }

    /** Check if user user is connected to network */
private fun checkInternet(){
    if(!internet)
        Toast.makeText(this, getString(R.string.interent), Toast.LENGTH_LONG).show()

}

    /** Setups buttons on click listeners */
    private fun setupButtons(){
        binding.backArrowAlbumDetail.setOnClickListener { onBackPressed() }
    }

    /** Initialize fotos recyclerview  */
    private fun initializeRecyclerViewFotos() {

        val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(2,
           StaggeredGridLayoutManager.VERTICAL)

        binding.albumFotos.layoutManager = layoutManager

    }

    /** Initialize comments recyclerview  */
    private fun initializeRecyclerViewComments() {

        val layoutManagerLinear: RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.albumComments.layoutManager = layoutManagerLinear


    }
    override fun showProgress() {
    binding.progressBar3.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar3.visibility = View.GONE
    }


    override fun onResponseFailure(message: String) {
        binding.progressBar3.visibility = View.GONE
        Toast.makeText(this,
            getString(R.string.error) + message,
            Toast.LENGTH_LONG).show()
        Log.d(ContentValues.TAG, message)
    }

    /** Setups Album images and comments recycler view */
    override fun setDataToRecyclerView(data: AlbumDetail?) {

        runOnUiThread {
if(data != null){

    // Setup fotos recyclerview
    val adapter = AlbumFotosAdapter()
    adapter.fotos = data.images
        binding.albumFotos.adapter = adapter


    // Setup comments recyclerview
    val adapterComments = AlbumCommentsAdapter(
        AlbumRepository(api, sessionManager, albumDao),
        this,
    data.album.id)

    adapterComments.comments = data.comments
    binding.albumComments.adapter = adapterComments

        }
        }
    }


    /** Addition of other data such as nick, profile picture ... */
    @SuppressLint("SetTextI18n")
    override fun setData(data: AlbumDetail?) {
        if(data != null){

        binding.albumName.text = data.album.title
            binding.ownerName.text = data.users[0].nick
            binding.albumDescription.text = "Description: " + data.album.description

            // Set profile image
            Picasso.get().load(data.users[0].avatar_url).placeholder(R.drawable.ic_baseline_account_circle_24)
                .error(R.drawable.ic_baseline_account_circle_24).into(binding.profileImageDetail)


            // Sets sex image to ImageView based on given string
            if (data.users[0].sex == "Woman")
                binding.imageViewGenderDetail.setImageDrawable(
                    ContextCompat.getDrawable(binding.imageViewGenderDetail.context,
                        R.drawable.ic_gender_female))
            else
                binding.imageViewGenderDetail.setImageDrawable(
                    ContextCompat.getDrawable(binding.imageViewGenderDetail.context,
                        R.drawable.ic_gender_male))
        }

    }
}