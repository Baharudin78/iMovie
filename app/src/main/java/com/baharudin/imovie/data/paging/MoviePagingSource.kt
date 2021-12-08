package com.baharudin.imovie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baharudin.imovie.data.remote.model.Movie
import com.baharudin.imovie.data.remote.service.MovieApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
class MoviePagingSource(
    private val movieApi: MovieApi
) :PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = movieApi.getNowPlaying(pageIndex)
            val movie = response.results
            LoadResult.Page(
                data = movie,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex -1,
                nextKey = if (movie.isEmpty()) null else pageIndex +1)
        }catch (e : IOException) {
            return LoadResult.Error(e)
        }catch (e : HttpException) {
            return LoadResult.Error(e)
        }
    }
}