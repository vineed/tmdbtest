package com.vin.imdbtestapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vin.imdbtestapp.repo.TMDBRepository
import com.vin.imdbtestapp.repo.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

val TAG = HomeViewModel::class.simpleName

@HiltViewModel
class HomeViewModel @Inject constructor(private val tmdbRepository: TMDBRepository) : ViewModel() {

    private var trendingMovies: List<Movie> = emptyList()
    private var _movies = MutableStateFlow(emptyList<Movie>())
    val movies: StateFlow<List<Movie>> get() = _movies

    private var _search = MutableStateFlow("")
    val search: StateFlow<String> get() = _search
    var isInit = true

    init {
        fetchTrendingMovies()
    }

    private fun fetchTrendingMovies() {
        Log.d(TAG, "fetchTrendingMoview: ")
        viewModelScope.launch {
            //TODO its possible to create further separation i.e domain model and view model
            val result = tmdbRepository.getTrendingMovies()

            if (result.isSuccess) {
                trendingMovies = result.getOrDefault(emptyList())

                refreshLikedMovies()

                Log.d(TAG, "fetchTrendingMovies: movies::$trendingMovies")
            } else {
                Log.d(TAG, "fetchTrendingMovies: error")
                //TODO error state
            }
        }
    }

    fun refreshLikedMovies() {
        Log.d(TAG, "refreshLikedMovies: ")

        viewModelScope.launch {
            val likedMoviesResult = tmdbRepository.getLikedIds()
            if (likedMoviesResult.isSuccess) {
                Log.d(TAG, "refreshLikedMovies:likedMoviesResult:: ${likedMoviesResult.getOrNull()}")
                _movies.value = trendingMovies.onEach { movie: Movie ->
                    movie.isLiked = likedMoviesResult.getOrNull()?.any { it == movie.id } == true
                }
            } else {
                Log.d(TAG, "refreshLikedMovies: failure")
                //TODO error state
            }
        }
    }

    fun searchMovies(searchMovie: String) {
        Log.d(TAG, "searchMovies: searchMovie::$searchMovie")
        _search.value = searchMovie

        if (searchMovie.isBlank()) {
            _movies.value = trendingMovies
        } else {
            viewModelScope.launch {
                val result = tmdbRepository.searchMovieByName(searchMovie)

                if (result.isSuccess) {
                    _movies.value = result.getOrDefault(emptyList())
                    Log.d(TAG, "fetchTrendingMovies: movies::${_movies.value}")
                } else {
                    Log.d(TAG, "fetchTrendingMovies: error")
                    //TODO error state
                }
            }
        }
    }

}