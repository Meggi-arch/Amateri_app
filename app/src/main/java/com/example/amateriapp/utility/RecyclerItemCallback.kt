package com.example.amateriapp.utility

import com.example.amateriapp.data.model.Album
import com.example.amateriapp.data.model.User

/** Callback when item is recyclerview was clicked */
interface RecyclerItemCallback {

    /** Called when item is selected */
    fun onItemClick(notice: Album?)

}