package com.baharudin.imovie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.baharudin.imovie.data.local.dao.MovieDao
import com.baharudin.imovie.data.local.model.MovieLocal

@Database(
    entities = [MovieLocal::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}