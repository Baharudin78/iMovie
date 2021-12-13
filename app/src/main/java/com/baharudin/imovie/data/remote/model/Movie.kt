package com.baharudin.imovie.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
        val id: String,
        val overview : String?,
        val poster_path: String,
        val original_title: String,
        val release_date: String,
        val video: Boolean,
        val backdrop_path : String,
        val vote_average : Float
        ) : Parcelable {
                val baseUrl get() = "https://image.tmdb.org/t/p/w500"
        }