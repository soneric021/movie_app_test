package com.ericsonmontero.moviewtechnicaltest.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ericsonmontero.moviewtechnicaltest.data.local.MovieDao
import com.ericsonmontero.moviewtechnicaltest.data.local.MovieDatabase
import com.ericsonmontero.moviewtechnicaltest.data.local.models.MovieEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {
    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setupDatabase(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        movieDao = database.movieDao()
    }

    @After
    fun closeDatabase(){
        database.close()
    }

    @Test
    fun insertMovies_returnTrue() = runBlocking {
        val movie = MovieEntity("111", "title", "22 jul 2022", "", "plot", "cal, mark")
        movieDao.insertMovies(listOf(movie))

        val latch = CountDownLatch(1)

        val job = async(Dispatchers.IO) {
            val movies = movieDao.getMovies()
            assertThat(movies).contains(movie)
            latch.countDown()
        }

        job.await()
        job.cancelAndJoin()
    }

    @Test
    fun deleteMovies_returnTrue() = runBlocking {
        val movie = MovieEntity("111", "title", "22 jul 2022", "", "plot", "cal, mark")
        val movie2 =  MovieEntity("112", "title", "22 jul 2022", "", "plot", "cal, mark")

        movieDao.insertMovies(listOf(movie, movie2))

        movieDao.deleteMovies()


        val latch = CountDownLatch(1)

        val job = async(Dispatchers.IO) {
            val movies = movieDao.getMovies()
            assertThat(movies).doesNotContain(movie)
            latch.countDown()
        }

        job.await()
        job.cancelAndJoin()
    }
}