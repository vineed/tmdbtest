package com.vin.imdbtestapp.repo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vin.imdbtestapp.repo.db.models.MovieEntity
import com.vin.imdbtestapp.repo.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT id FROM movie WHERE isFavourite=1")
    suspend fun getLikedMoviesIDS(): List<Int>

    @Query("SELECT * FROM movie WHERE id=:movieId")
    suspend fun getMovie(movieId: Int): MovieEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun likeMovie(movieEntity: MovieEntity): Int
}