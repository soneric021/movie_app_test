package com.ericsonmontero.moviewtechnicaltest.domain.use_case

import com.ericsonmontero.moviewtechnicaltest.data.local.models.toDomain
import com.ericsonmontero.moviewtechnicaltest.data.models.toDomain
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import com.ericsonmontero.moviewtechnicaltest.domain.models.Resource
import com.ericsonmontero.moviewtechnicaltest.domain.repository.MovieRepository
import com.ericsonmontero.moviewtechnicaltest.utils.Utils
import com.ericsonmontero.moviewtechnicaltest.utils.orderByDateDescending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(orderByDescending:Boolean): Flow<Resource<List<MovieModel>>> {
        return flow {
            try{
                val movies = repository.getMoviesListFromDb()

                if(movies.isNotEmpty()) {
                    val localMovies = movies.map { it.toDomain() }.orderByDateDescending(orderByDescending)
                    emit(Resource.Success(localMovies))
                    return@flow
                }

                val moviesResponse = repository.getMoviesList()
                if(moviesResponse.isSuccessful) {
                    moviesResponse.body()?.let { response ->
                        val moviesRemote = response.items.map { it.toDomain() }.orderByDateDescending(orderByDescending)
                        emit(Resource.Success(moviesRemote))
                        repository.saveMovies(response.items)
                    }
                } else {
                    emit(Resource.Error("Hubo un error al intentar cargar las peliculas."))
                }
            }catch (ex:HttpException){
                ex.printStackTrace()
                emit(Resource.Error("Hubo un error al intentar cargar las peliculas."))
            }catch (ex:IOException){
                ex.printStackTrace()
                emit(Resource.Error("Hubo un error al conectarse con el servidor"))
            }

        }
    }
}