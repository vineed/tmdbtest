package com.vin.imdbtestapp.movie_details.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vin.imdbtestapp.repo.TMDBRepository
import com.vin.imdbtestapp.repo.db.dao.MovieDao
import com.vin.imdbtestapp.repo.mapper.toMovie
import com.vin.imdbtestapp.repo.mapper.toMovieEntity
import com.vin.imdbtestapp.repo.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

val TAG = MovieDetailViewModel::class.simpleName

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val tmdbRepository: TMDBRepository, val movieDao: MovieDao) :
    ViewModel() {
    private var _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> get() = _movie

    fun retrieveCachedMovie(movieId: Int) {
        Log.d(TAG, "retrieveCachedMovie: ")
        viewModelScope.launch {
            val result = tmdbRepository.getMovieById(movieId)

            if (result.isSuccess) {
                _movie.value = result.getOrNull()
            } else {
                //TODO handle error
            }
        }
    }

    fun likeMovie(like: Boolean) {
        Log.d(TAG, "likeMovie: like::$like")

        viewModelScope.launch {
            val movie = movie.value?.copy(isLiked = like)
            movie?.toMovieEntity()?.let { movieEntity ->
                val isUpdated = movieDao.likeMovie(movieEntity) >= 1
                Log.d(TAG, "likeMovie: isUpdated::$isUpdated")
                if(isUpdated) {
                    _movie.value = movie
                }
            }
        }
    }
}