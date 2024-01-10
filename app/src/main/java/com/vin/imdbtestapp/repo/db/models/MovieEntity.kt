package com.vin.imdbtestapp.repo.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(true) @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String? = "",
    @ColumnInfo("description") val description: String? = "",
    @ColumnInfo("contentUrl") val contentUrl: String? = "",
    @ColumnInfo("isFavourite") val isFavourite: Int = 0,
)