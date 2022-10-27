package com.ericsonmontero.moviewtechnicaltest.data.remote

import com.ericsonmontero.moviewtechnicaltest.data.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movies.json")
    suspend fun getMoviesList(@Query("key") key:String = API_KEY): Response<MovieResponse>

    companion object{
        const val API_KEY = "cb03b960"
    }
}