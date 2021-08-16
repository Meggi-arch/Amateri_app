package com.example.amateriapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.amateriapp.R
import com.example.amateriapp.data.model.Album
import com.example.amateriapp.data.model.UserX
import com.example.amateriapp.databinding.AlbumItemBinding
import com.example.amateriapp.repository.AlbumRepository
import com.example.amateriapp.utility.UserCallback
import com.example.amateriapp.utility.RecyclerItemCallback
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Recycler adapter for list of albums
 */
class AlbumAdapter @Inject constructor(
    private var recyclerItemCallback: RecyclerItemCallback?,
    private var albumRepository: AlbumRepository,
    private var ac: Activity
): RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {


    /** View holder for album preview */
    inner class AlbumViewHolder(val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var albums: List<Album>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = albums.size

    /** Creates layout */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(AlbumItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    /** Binds object and listener to item view  */
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {


        holder.binding.apply {
            val album = albums[position]

            //Get user from repository and set it to view
          val userInfo: UserCallback =
               object : UserCallback {
                    override fun getUserFromRepository(cachedUser: UserX) {

    ac.runOnUiThread {
    albumTextViewName.text = cachedUser.nick

    Picasso.get().load(cachedUser.avatar_url).placeholder(R.drawable.ic_baseline_account_circle_24)
        .error(R.drawable.ic_baseline_account_circle_24).into(profileImage)

    // Sets sex image to ImageView based on given string
    if (cachedUser.sex == "Woman")
        sexImageView.setImageDrawable(
            ContextCompat.getDrawable(sexImageView.context,
                R.drawable.ic_gender_female))
    else
        sexImageView.setImageDrawable(
            ContextCompat.getDrawable(sexImageView.context,
                R.drawable.ic_gender_male))
}
                    }
                }

                albumRepository.getUser(
                    album.user_id,
                    album.id,
                    userInfo)

            albumTextView.text = album.title

            Picasso.get().load(album.thumb_url).into(albumImageView, object : com.squareup.picasso.Callback{
                override fun onSuccess() {
                    progressBar4.visibility = View.GONE
                    albumImageView.visibility = View.VISIBLE

                }

             // Not implemented
                override fun onError(e: Exception?) {}

            })

            albumCard.setOnClickListener {
                recyclerItemCallback?.onItemClick(album)
            }

        }

    }

}