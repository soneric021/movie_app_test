package com.ericsonmontero.moviewtechnicaltest.data.models

data class MovieResponse(
    val errorMessage: String,
    val items: List<Item>
)