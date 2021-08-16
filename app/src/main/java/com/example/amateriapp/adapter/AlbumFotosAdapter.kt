package com.example.amateriapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.amateriapp.data.model.Image
import com.example.amateriapp.databinding.AlbumFotoItemBinding
import com.squareup.picasso.Picasso


/**
 * Recycler adapter for list of fotos
 */
class AlbumFotosAdapter: RecyclerView.Adapter<AlbumFotosAdapter.FotoViewHolder>() {


    /** View holder for album preview */
    inner class FotoViewHolder(val binding: AlbumFotoItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
     var fotos: List<Image>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = fotos.size

    /** Creates layout */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FotoViewHolder {
        return FotoViewHolder(
            AlbumFotoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }



    /** Binds object and listener to item view  */
    override fun onBindViewHolder(holder: FotoViewHolder, position: Int) {

        holder.binding.apply {
            val foto = fotos[position]

            Picasso.get().load(foto.url).into(imageViewAlbumFoto, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    progressBar5.visibility = View.GONE
                    imageViewAlbumFoto.visibility = View.VISIBLE
                }

                // Not implemented
                override fun onError(e: Exception?) {}

            })




        }
    }

}