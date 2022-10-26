package com.ericsonmontero.moviewtechnicaltest.domain.use_case

import com.ericsonmontero.moviewtechnicaltest.data.local.models.toDomain
import com.ericsonmontero.moviewtechnicaltest.data.models.toDomain
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import com.ericsonmontero.moviewtechnicaltest.domain.models.Resource
import com.ericsonmontero.moviewtechnicaltest.domain.repository.MovieRepository
import com.ericsonmontero.moviewtechnicaltest.utils.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(): Flow<Resource<List<MovieModel>>> {
        return flow {
            emit(Resource.Loading)

            val movies = repository.getMoviesListFromDb()

            val isDbEmpty = movies.isEmpty()

            if(!isDbEmpty) {
                emit(Resource.Success(movies.map { it.toDomain() }.sortedByDescending { Utils.parseDate(it.releaseDate) }))
                return@flow
            }

            val moviesResponse = repository.getMoviesList()
            if(moviesResponse.isSuccessful){
                moviesResponse.body()?.let { response ->
                    emit(Resource.Success(response.items.map { it.toDomain() }.sortedByDescending { Utils.parseDate(it.releaseDate) }))
                    repository.saveMovies(response.items)
                }
            }else{
               emit(Resource.Error("Hubo un error al intentar cargar las peliculas."))
            }

        }
    }
}