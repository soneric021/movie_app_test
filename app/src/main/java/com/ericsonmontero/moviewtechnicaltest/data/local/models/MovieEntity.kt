package com.ericsonmontero.moviewtechnicaltest.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel

@Entity(tableName = "tb_movie")
data class MovieEntity(
    @PrimaryKey
    val uuid:String,
    val title:String,
    val releaseDate:String,
    val image:String,
    val plot:String,
    val stars:String,
    val genres:String,
    val imdbRating:String,
    val runtimeMins:String
)

fun MovieEntity.toDomain() : MovieModel{
    return MovieModel(
        title = title,
        releaseDate = releaseDate,
        image = image,
        plot = plot,
        stars = stars.split(","),
        id = uuid,
        genres = genres,
        imdbRating = imdbRating,
        runtimeMins =  runtimeMins
    )
}
