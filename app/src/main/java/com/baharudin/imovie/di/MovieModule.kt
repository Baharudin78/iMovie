package com.baharudin.imovie.di

import android.content.Context
import androidx.room.Room
import com.baharudin.imovie.data.local.database.MovieDatabase
import com.baharudin.imovie.data.remote.service.MovieApi
import com.baharudin.imovie.util.Constans.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMoshi() : Moshi = Moshi
        .Builder().run {
            add(KotlinJsonAdapterFactory())
                .build()
        }
    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(moshi: Moshi, okHttpClient: OkHttpClient) : MovieApi =
        Retrofit.Builder()
            .run {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                client(okHttpClient)
                build()
            }.create(MovieApi::class.java)
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        "movie.db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(
        movieDatabase: MovieDatabase
    ) = movieDatabase.movieDao()
}