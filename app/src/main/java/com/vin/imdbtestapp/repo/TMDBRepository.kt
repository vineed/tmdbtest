package com.vin.imdbtestapp.repo

import com.vin.imdbtestapp.repo.model.Movie

interface TMDBRepository{
    suspend fun getTrendingMovies(): Result<List<Movie>>
    suspend fun searchMovieByName(movieName: String): Result<List<Movie>>
    suspend fun getMovieById(movieId: Int): Result<Movie>
    suspend fun getLikedIds(): Result<List<Int>>
}