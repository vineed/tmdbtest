package com.vin.imdbtestapp.repo.api.models

import com.google.gson.annotations.SerializedName

data class MovieResultModelDTO(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("results") val movieDTOS: List<MovieDTO>,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0,
)