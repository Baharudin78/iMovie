package com.baharudin.imovie.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.baharudin.imovie.data.remote.model.Movie
import com.baharudin.imovie.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){
    private var getNowPlaying : Flow<PagingData<Movie>>? = null

    fun getNowPlaying() : Flow<PagingData<Movie>> {
        val nowPlayingResult : Flow<PagingData<Movie>> =
            movieRepository.getNowPlaying().cachedIn(viewModelScope)
        getNowPlaying = nowPlayingResult
        return nowPlayingResult
    }
}