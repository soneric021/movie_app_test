package com.ericsonmontero.moviewtechnicaltest.domain.use_case

import com.ericsonmontero.moviewtechnicaltest.data.local.models.toDomain
import com.ericsonmontero.moviewtechnicaltest.data.models.toDomain
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import com.ericsonmontero.moviewtechnicaltest.domain.models.Resource
import com.ericsonmontero.moviewtechnicaltest.domain.repository.MovieRepository
import com.ericsonmontero.moviewtechnicaltest.utils.Utils
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
            emit(Resource.Loading)

            try{
                val movies = repository.getMoviesListFromDb()

                val isDbEmpty = movies.isEmpty()

                if(!isDbEmpty) {
                    val localMovies = if(orderByDescending) movies.map { it.toDomain() }.sortedByDescending { Utils.parseDate(it.releaseDate) } else  movies.map { it.toDomain() }
                    emit(Resource.Success(localMovies))
                    return@flow
                }

                val moviesResponse = repository.getMoviesList()
                if(moviesResponse.isSuccessful){
                    moviesResponse.body()?.let { response ->
                        val moviesRemote = if(orderByDescending) response.items.map { it.toDomain() }.sortedByDescending { Utils.parseDate(it.releaseDate) } else response.items.map { it.toDomain() }
                        emit(Resource.Success(moviesRemote))
                        repository.saveMovies(response.items)
                    }
                }else{
                    emit(Resource.Error("Hubo un error al intentar cargar las peliculas."))
                }
            }catch (ex:HttpException){
                ex.printStackTrace()
                emit(Resource.Error("Hubo un error al intentar cargar las peliculas."))
            }catch (ex:IOException){
                ex.printStackTrace()
                emit(Resource.Error("Hubo un error al intentar cargar las peliculas."))
            }catch (ex:Exception){

            }

        }
    }
}