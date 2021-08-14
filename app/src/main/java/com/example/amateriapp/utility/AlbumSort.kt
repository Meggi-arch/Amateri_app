package com.example.amateriapp.utility

/** Enum represents type of sorting albums to get from API call or from database */
enum class AlbumSort {
    TIME   {
        override fun toString(): String {
            return "time" }
    },
    COMMENTS{
        override fun toString(): String {
            return "comments" }
    },
    STANDARD{
        override fun toString(): String {
            return "standard" }
    }

}