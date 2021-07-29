package com.example.amateriapp.utility

import com.example.amateriapp.data.model.Album

interface RecyclerItemClickListener {
    fun onItemClick(notice: Album?)
}