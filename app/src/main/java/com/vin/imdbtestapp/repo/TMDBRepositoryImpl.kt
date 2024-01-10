package com.vin.imdbtestapp.repo

import android.content.Context
import android.util.Log
import com.vin.imdbtestapp.repo.api.TMDBAPI
import com.vin.imdbtestapp.repo.db.TMDBDatabase
import com.vin.imdbtestapp.repo.db.dao.MovieDao
import com.vin.imdbtestapp.repo.mapper.toMovie
import com.vin.imdbtestapp.repo.mapper.toMovieEntity
import com.vin.imdbtestapp.repo.model.Movie
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception

val TAG = TMDBRepositoryImpl::class.simpleName
class TMDBRepositoryImpl(private val tmdbapi: TMDBAPI, private val movieDao: MovieDao) : TMDBRepository {

    override suspend fun getTrendingMovies(): Result<List<Movie>> {
        return try {
            val trendingMovies = tmdbapi.getTrendingMovies()

            val trendingMoviesDomain = trendingMovies.movieDTOS.map { it.toMovie() }

            //TODO could use bulk insert
            trendingMoviesDomain.forEach { movie ->
                movieDao.addMovie(movie.toMovieEntity())
            }

            Result.success(trendingMoviesDomain)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    override suspend fun getLikedIds(): Result<List<Int>> {
        Log.d(TAG, "getLikedIds: ")

        return try {
            val likeMoviesIds = movieDao.getLikedMoviesIDS()

            Result.success(likeMoviesIds)
        }
        catch (ex: Exception) {
            ex.printStackTrace()

            Result.failure(ex)
        }
    }

    override suspend fun searchMovieByName(movieName: String): Result<List<Movie>> {
        return try {
            val trendingMovies = tmdbapi.searchMovie(movieName)

            Result.success(trendingMovies.movieDTOS.map { it.toMovie() })
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    override suspend fun getMovieById(movieId: Int): Result<Movie> {
        return try {
            val movieEntity = movieDao.getMovie(movieId)

            Result.success(movieEntity.toMovie())
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}