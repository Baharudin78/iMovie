package com.baharudin.imovie.data.local.dao

import androidx.room.*
import com.baharudin.imovie.data.local.model.MovieLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorit(movieResponse: MovieLocal)

    @Query("SELECT * FROM movie_list")
    suspend fun getAllFavorit() : Flow<List<MovieLocal>>

    @Delete
    suspend fun deleteFavorit(movieResponse: MovieLocal)
}