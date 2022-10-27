package com.ericsonmontero.moviewtechnicaltest.domain.repository

import com.ericsonmontero.moviewtechnicaltest.data.local.models.MovieEntity
import com.ericsonmontero.moviewtechnicaltest.data.models.Item
import com.ericsonmontero.moviewtechnicaltest.data.models.MovieResponse
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import retrofit2.Response

interface MovieRepository {
    suspend fun getMoviesList(): Response<MovieResponse>
    suspend fun getMoviesListFromDb() : List<MovieEntity>
    suspend fun saveMovies(items:List<Item>)
}