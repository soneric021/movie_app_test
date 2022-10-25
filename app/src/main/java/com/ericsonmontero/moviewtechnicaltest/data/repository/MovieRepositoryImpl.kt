package com.ericsonmontero.moviewtechnicaltest.data.repository

import com.ericsonmontero.moviewtechnicaltest.data.local.MovieDao
import com.ericsonmontero.moviewtechnicaltest.data.local.models.MovieEntity
import com.ericsonmontero.moviewtechnicaltest.data.models.Item
import com.ericsonmontero.moviewtechnicaltest.data.models.MovieResponse
import com.ericsonmontero.moviewtechnicaltest.data.models.toMovieEntity
import com.ericsonmontero.moviewtechnicaltest.data.remote.MovieApi
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import com.ericsonmontero.moviewtechnicaltest.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApi: MovieApi, private val movieDao: MovieDao) : MovieRepository {
    override fun getMoviesList(): Response<MovieResponse> {
       return movieApi.getMoviesList()
    }

    override fun getMoviesListFromDb(): List<MovieEntity> {
        return movieDao.getMovies()
    }

    override fun saveMovies(items: List<Item>) {
        movieDao.deleteMovies()
        movieDao.insertMovies(items.map { it.toMovieEntity() })
    }

}