package com.ericsonmontero.moviewtechnicaltest.data

import com.ericsonmontero.moviewtechnicaltest.data.local.models.MovieEntity
import com.ericsonmontero.moviewtechnicaltest.data.models.Item
import com.ericsonmontero.moviewtechnicaltest.data.models.MovieResponse
import com.ericsonmontero.moviewtechnicaltest.data.models.toMovieEntity
import com.ericsonmontero.moviewtechnicaltest.data.remote.MovieApi
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import com.ericsonmontero.moviewtechnicaltest.domain.repository.MovieRepository
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class FakeMovieRepository(private val api:MovieApi) : MovieRepository {
    private val data:ArrayList<MovieEntity> = arrayListOf()
    override suspend fun getMoviesList(): Response<MovieResponse> {
        return api.getMoviesList()
    }

    override suspend fun getMoviesListFromDb(): List<MovieEntity> {
        return data
    }

    override suspend fun saveMovies(items: List<Item>) {
        data.addAll(items.map { it.toMovieEntity() })
    }
    companion object{
        fun getMockFakeRepository(mockWebServer:MockWebServer):FakeMovieRepository {
             val client = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .build()

             val api = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(MovieApi::class.java)

            return FakeMovieRepository(api)
        }


    }
}
