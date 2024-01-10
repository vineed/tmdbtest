package com.vin.imdbtestapp.repo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vin.imdbtestapp.repo.db.dao.MovieDao
import com.vin.imdbtestapp.repo.db.models.MovieEntity
import com.vin.imdbtestapp.utils.DB_NAME

@Database(entities = [MovieEntity::class], version = 1)
abstract class TMDBDatabase : RoomDatabase() {
    companion object {
        private lateinit var tmdbDatabase: TMDBDatabase

        fun getDatabase(context: Context): TMDBDatabase {
            if (!::tmdbDatabase.isInitialized) {
                tmdbDatabase =
                    Room.databaseBuilder(context, TMDBDatabase::class.java, DB_NAME).build()
            }

            return tmdbDatabase
        }
    }

    public abstract fun movieDao(): MovieDao
}