package com.ericsonmontero.moviewtechnicaltest.data

import com.ericsonmontero.moviewtechnicaltest.data.models.toDomain
import com.ericsonmontero.moviewtechnicaltest.data.remote.MovieApi
import com.ericsonmontero.moviewtechnicaltest.data.repository.MovieRepositoryImpl
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import com.ericsonmontero.moviewtechnicaltest.enqueueResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

class MovieRepositoryTest {

    private val mockWebServer = MockWebServer()
    private val repository = FakeMovieRepository.getMockFakeRepository(mockWebServer)

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun should_fetch_movies_given_200_response(){
        runBlocking {
            mockWebServer.enqueueResponse("movies.json", 200)

            val actual = repository.getMoviesList().body()?.items?.map { it.toDomain() }

            val expected =  MovieModel(
                "tt1649418",
                "The Gray Man",
                "22 Jul 2022",
                "https://m.media-amazon.com/images/M/MV5BOWY4MmFiY2QtMzE1YS00NTg1LWIwOTQtYTI4ZGUzNWIxNTVmXkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_UX128_CR0,4,128,176_AL_.jpg",
                "When the CIA's most skilled operative-whose true identity is known to none-accidentally uncovers dark agency secrets, a psychopathic former colleague puts a bounty on his head, setting off a global manhunt by international assassins.",
                listOf("Ryan Gosling","Chris Evans","Ana de Armas", "Billy Bob Thornton"),
                "Action, Thriller",
                "6.6",
                "122"
            )

            Truth.assertThat(actual).contains(expected)

        }
    }

}