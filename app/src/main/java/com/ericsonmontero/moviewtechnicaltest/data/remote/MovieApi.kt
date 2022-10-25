package com.ericsonmontero.moviewtechnicaltest.data.remote

import com.ericsonmontero.moviewtechnicaltest.data.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("movies.json?key=cb03b960")
    fun getMoviesList(): Response<MovieResponse>
}