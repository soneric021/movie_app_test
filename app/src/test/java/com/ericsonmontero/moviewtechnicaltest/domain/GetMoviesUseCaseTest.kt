package com.ericsonmontero.moviewtechnicaltest.domain

import app.cash.turbine.test
import com.ericsonmontero.moviewtechnicaltest.data.FakeMovieRepository
import com.ericsonmontero.moviewtechnicaltest.domain.models.Resource
import com.ericsonmontero.moviewtechnicaltest.domain.use_case.GetMoviesUseCase
import com.ericsonmontero.moviewtechnicaltest.enqueueResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Test


class GetMoviesUseCaseTest {

    private lateinit var getMoviesUseCase:GetMoviesUseCase
    private lateinit var fakeMovieRepository: FakeMovieRepository
    private val mockWebServer = MockWebServer()

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Before
    fun setUp(){
        fakeMovieRepository = FakeMovieRepository.getMockFakeRepository(mockWebServer)
        getMoviesUseCase = GetMoviesUseCase(fakeMovieRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun should_emit_movieModel_and_save_movies() = runBlocking{

        mockWebServer.enqueueResponse("movies.json", 200)

        getMoviesUseCase.invoke(false).test {
            val movieitem = awaitItem()
            awaitComplete()
            Truth.assertThat(movieitem).isInstanceOf(Resource.Success::class.java)
        }

        val moviesFromDb = fakeMovieRepository.getMoviesListFromDb()
        Truth.assertThat(moviesFromDb).isNotEmpty()
    }

    @Test
    fun should_emits_error_when_response_code_is_not_200() = runBlocking {
        mockWebServer.enqueueResponse("movies.json", 400)

        getMoviesUseCase.invoke(false).test {
            val movieItem = awaitItem()
            awaitComplete()
            Truth.assertThat(movieItem).isInstanceOf(Resource.Error::class.java)
        }

    }

    @Test
    fun should_thrown_exception_when_no_internet_connection() = runBlocking  {
        val response = MockResponse()
            .setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        mockWebServer.enqueue(response)

        getMoviesUseCase.invoke(false).test{
            val movieItem = awaitItem()
            awaitComplete()
            Truth.assertThat(movieItem).isInstanceOf(Resource.Error::class.java)
        }
    }
}