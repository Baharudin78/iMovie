package com.baharudin.imovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.baharudin.imovie.data.paging.MoviePagingSource
import com.baharudin.imovie.data.remote.model.Movie
import com.baharudin.imovie.data.remote.service.MovieApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi) {

    fun getNowPlaying() : Flow<PagingData<Movie>> = Pager (
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {MoviePagingSource(movieApi)}
    ).flow

}