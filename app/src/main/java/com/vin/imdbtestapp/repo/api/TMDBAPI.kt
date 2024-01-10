package com.vin.imdbtestapp.repo.api

import com.vin.imdbtestapp.repo.api.models.MovieResultModelDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBAPI {
    @GET("trending/all/day")
    suspend fun getTrendingMovies(@Query("language") day:String = "en-US"): MovieResultModelDTO

    @GET("search/tv")
    suspend fun searchMovie(@Query("query") movieName: String): MovieResultModelDTO
}