package com.ericsonmontero.moviewtechnicaltest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ericsonmontero.moviewtechnicaltest.data.local.models.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * from tb_movie")
    fun getMovies() : List<MovieEntity>

    @Insert
    fun insertMovies( movieEntity: List<MovieEntity>)

    @Query("DELETE FROM tb_movie")
    fun deleteMovies()
}