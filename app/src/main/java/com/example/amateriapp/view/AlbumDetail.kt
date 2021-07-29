package com.example.amateriapp.view

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.amateriapp.R
import com.example.amateriapp.adapter.AlbumCommentsAdapter
import com.example.amateriapp.adapter.AlbumFotosAdapter
import com.example.amateriapp.data.model.AlbumDetail
import com.example.amateriapp.data.network.AmaterApi
import com.example.amateriapp.databinding.ActivityAlbumDetailBinding
import com.example.amateriapp.presenter.AlbumDetailActivityPresenter
import com.example.amateriapp.repository.AlbumDetailRepository
import com.example.amateriapp.utility.AlbumDetailActivityContract
import com.example.amateriapp.utility.Constant.USER_ID
import com.example.amateriapp.utility.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class AlbumDetail : AppCompatActivity(), AlbumDetailActivityContract.MainView  {

    @Inject
    @Named("albumDetail")
    lateinit var api: AmaterApi

    @JvmField
    @Inject
    var internet: Boolean = false

    private var presenter: AlbumDetailActivityContract.Presenter? = null

    private lateinit var binding: ActivityAlbumDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeRecyclerViewFotos()

        initializeRecyclerViewComments()

        if(!internet){
            Toast.makeText(this, getString(R.string.interent), Toast.LENGTH_LONG).show()
        }
        val id = intent.getIntExtra(USER_ID,0)

        val  sessionManager = SessionManager(this)

        presenter = AlbumDetailActivityPresenter(this, AlbumDetailRepository(api),sessionManager,id)


        (presenter as AlbumDetailActivityPresenter).requestDataFromServer()


        binding.backArrowAlbumDetail.setOnClickListener { onBackPressed() }
    }

    private fun initializeRecyclerViewFotos() {

        val layoutManager: RecyclerView.LayoutManager = StaggeredGridLayoutManager(2,
           StaggeredGridLayoutManager.VERTICAL)

        binding.albumFotos.layoutManager = layoutManager

    }

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

    override fun setDataToRecyclerView(data: AlbumDetail?) {

        runOnUiThread {
if(data != null){


    val adapter = AlbumFotosAdapter()

    adapter.fotos = data.images

        binding.albumFotos.adapter = adapter

    val adapterComments = AlbumCommentsAdapter()

    adapterComments.comments = data.comments

    binding.albumComments.adapter = adapterComments

        }


        }



    }

    @SuppressLint("SetTextI18n")
    override fun setData(data: AlbumDetail?) {
        if(data != null){
            binding.albumName.text = data.album.title
            binding.ownerName.text = data.users[0].nick
            binding.albumDescription.text = "Description: " + data.album.description
        }

    }
}