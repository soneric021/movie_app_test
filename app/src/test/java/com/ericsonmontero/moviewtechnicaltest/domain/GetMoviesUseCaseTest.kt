package com.ericsonmontero.moviewtechnicaltest.domain

import app.cash.turbine.test
import com.ericsonmontero.moviewtechnicaltest.data.FakeMovieRepository
import com.ericsonmontero.moviewtechnicaltest.data.models.toMovieEntity
import com.ericsonmontero.moviewtechnicaltest.domain.models.Resource
import com.ericsonmontero.moviewtechnicaltest.domain.use_case.GetMoviesUseCase
import com.ericsonmontero.moviewtechnicaltest.enqueueResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
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
            Truth.assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            val movieitem = awaitItem()
            Truth.assertThat(movieitem).isInstanceOf(Resource.Success::class.java)

        }
    }

    @Test
    fun should_emits_error() = runBlocking {
        mockWebServer.enqueueResponse("movies.json", 400)

        getMoviesUseCase.invoke(false).test {
            Truth.assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            Truth.assertThat(awaitItem()).isInstanceOf(Resource.Error::class.java)
        }

    }
}