package com.baharudin.imovie.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movie_list")
@Parcelize
data class MovieLocal(
    var id_movie: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val video: Boolean
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}