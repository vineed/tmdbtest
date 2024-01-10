package com.vin.imdbtestapp.repo.model

data class Movie(val id: Int, val name: String, val desc: String?, val contentUrl: String?, var isLiked: Boolean = false)