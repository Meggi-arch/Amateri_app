package com.example.amateriapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.amateriapp.R
import com.example.amateriapp.data.model.Comment
import com.example.amateriapp.data.model.UserX
import com.example.amateriapp.databinding.CommentItemBinding
import com.example.amateriapp.repository.AlbumRepository
import com.example.amateriapp.utility.UserCallback
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Recycler adapter for list of comments
 */
class AlbumCommentsAdapter @Inject constructor(
    private var albumRepository: AlbumRepository,
    private var ac: Activity,
    private var albumId: Int
) : RecyclerView.Adapter<AlbumCommentsAdapter.CommentViewHolder>() {

    /** View holder for album preview */
    inner class CommentViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var comments: List<Comment>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = comments.size

    /** Creates layout */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            CommentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    /** Binds object and listener to item view  */
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        holder.binding.apply {
            val comment = comments[position]

            //Get user from repository and set it to view
            val userInfo: UserCallback =
                object : UserCallback {
                    override fun getUserFromRepository(cachedUser: UserX) {

                        ac.runOnUiThread {

                            senderName.text = cachedUser.nick

                            Picasso.get().load(cachedUser.avatar_url).placeholder(R.drawable.ic_baseline_account_circle_24)
                                .error(R.drawable.ic_baseline_account_circle_24).into(profileImageComment)

                        }
                    }
                }

            albumRepository.getUser(
                comment.user_id,
                albumId,
                userInfo)


            commentItem.text = comment.message

        }
    }

}