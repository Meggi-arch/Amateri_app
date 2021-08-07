package com.example.amateriapp.view

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
import com.example.amateriapp.data.network.AmaterApi
import com.example.amateriapp.databinding.ActivityAlbumBinding
import com.example.amateriapp.presenter.AlbumActivityPresenter
import com.example.amateriapp.repository.AlbumRepository
import com.example.amateriapp.utility.AlbumActivityContract
import com.example.amateriapp.utility.Constant.TAG
import com.example.amateriapp.utility.Constant.USER_ID
import com.example.amateriapp.utility.RecyclerItemClickListener
import com.example.amateriapp.utility.preferences.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class AlbumActivity : AppCompatActivity(), AlbumActivityContract.MainView {

    @Inject
    @Named("album")
    lateinit var api: AmaterApi

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

        initializeRecyclerView()

        if(!internet){
            Toast.makeText(this, getString(R.string.interent), Toast.LENGTH_LONG).show()
        }


        presenter = AlbumActivityPresenter(this, AlbumRepository(api),sessionManager)


            (presenter as AlbumActivityPresenter).requestDataFromServer()


        binding.logout.setOnClickListener {

            sessionManager.setToken("null")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

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

     val recyclerItemClickListener: RecyclerItemClickListener =
        object : RecyclerItemClickListener {


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

    override fun setDataToRecyclerView(noticeArrayList: List<Album>) {

        runOnUiThread {

         val adapter = AlbumAdapter(recyclerItemClickListener)

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