package com.example.amateriapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.amateriapp.data.model.Album
import com.example.amateriapp.databinding.AlbumItemBinding
import com.example.amateriapp.utility.RecyclerItemClickListener
import com.squareup.picasso.Picasso
import java.lang.Exception


class AlbumAdapter(private var recyclerItemClickListener: RecyclerItemClickListener?): RecyclerView.Adapter<AlbumAdapter.TodoViewHolder>() {



    inner class TodoViewHolder(val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(AlbumItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        holder.binding.apply {
            val album = albums[position]
            albumTextView.text = album.title




            Picasso.get().load(album.thumb_url).into(albumImageView, object : com.squareup.picasso.Callback{
                override fun onSuccess() {
                    progressBar4.visibility = View.GONE
                    albumImageView.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    TODO("Not yet implemented")
                }

            })

            albumCard.setOnClickListener {
                recyclerItemClickListener?.onItemClick(album)
            }

        }
    }

}