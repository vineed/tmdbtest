package com.vin.imdbtestapp.di

import android.content.Context
import com.vin.imdbtestapp.repo.TMDBRepository
import com.vin.imdbtestapp.repo.TMDBRepositoryImpl
import com.vin.imdbtestapp.repo.api.TMDBAPI
import com.vin.imdbtestapp.repo.db.TMDBDatabase
import com.vin.imdbtestapp.repo.db.dao.MovieDao
import com.vin.imdbtestapp.utils.TMDB_API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun getTMDBApi(): TMDBAPI {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NzVmZTVhNTEzNTIwNTQ4ODBlNTgzYzFmMzA1ZWI5YiIsInN1YiI6IjY1OTU4NzM0YTY5OGNmNWE4YzQzYTA1NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.0UeWV8q34z5BQ0tT7RcPAI5iDYPibIGbcNNSwezRN-g"
                    ).build()

                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(TMDB_API_BASE_URL).build()

        return retrofit.create(TMDBAPI::class.java)
    }

    @Singleton
    @Provides
    fun getTMDBDatabase(@ApplicationContext context: Context) : TMDBDatabase {
        return TMDBDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun getMovieDao(tmdbDatabase: TMDBDatabase) : MovieDao {
        return tmdbDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun getTMDBRepository(tmdbapi: TMDBAPI, movieDao: MovieDao): TMDBRepository {
        return TMDBRepositoryImpl(tmdbapi, movieDao)
    }
}