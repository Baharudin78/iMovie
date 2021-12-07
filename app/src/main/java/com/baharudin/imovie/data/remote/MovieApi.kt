package com.baharudin.imovie.data.remote

import com.baharudin.imovie.data.MovieResponse
import com.baharudin.imovie.util.Constans.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlaying(
        @Query("page") pageNumber : Int
    ) : MovieResponse
}