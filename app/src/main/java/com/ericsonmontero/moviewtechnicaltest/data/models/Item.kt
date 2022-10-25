package com.ericsonmontero.moviewtechnicaltest.data.models

import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel

data class Item(
    val contentRating: String,
    val directorList: List<Director>,
    val directors: String,
    val fullTitle: String,
    val genreList: List<Genre>,
    val genres: String,
    val id: String,
    val imDbRating: String,
    val imDbRatingCount: String,
    val image: String,
    val metacriticRating: String,
    val plot: String,
    val releaseState: String,
    val runtimeMins: String,
    val runtimeStr: String,
    val starList: List<Star>,
    val stars: String,
    val title: String,
    val year: String
)

fun Item.toDomain():MovieModel {
    return MovieModel(
        title = title,
        image = image,
        releaseDate = releaseState,
        plot = plot,
        stars = starList.map { it.name }
    )
}