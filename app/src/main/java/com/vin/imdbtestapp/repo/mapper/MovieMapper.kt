package com.vin.imdbtestapp.repo.mapper

import com.vin.imdbtestapp.repo.api.models.MovieDTO
import com.vin.imdbtestapp.repo.db.models.MovieEntity
import com.vin.imdbtestapp.repo.model.Movie
import com.vin.imdbtestapp.utils.TMDB_IMAGE_BASE_URL

fun MovieDTO.toMovie(): Movie {
    return Movie(this.id?:-1, this.title?.takeIf { it.isNotBlank() }?:this.name?:"-", this.overview?:"", "$TMDB_IMAGE_BASE_URL${this.backdropPath}")
}

fun MovieEntity.toMovie(): Movie {
    return Movie(this.id, this.name?:"-", this.description, this.contentUrl, this.isFavourite > 1)
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(this.id, this.name, this.desc, this.contentUrl, if(this.isLiked) 1 else 0)
}

//fun Movie.toMovieCache: {}