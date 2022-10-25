package com.ericsonmontero.moviewtechnicaltest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ericsonmontero.moviewtechnicaltest.data.local.models.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao():MovieDao
}