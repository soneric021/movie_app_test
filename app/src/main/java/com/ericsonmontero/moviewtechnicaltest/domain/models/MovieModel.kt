package com.ericsonmontero.moviewtechnicaltest.domain.models

data class MovieModel(
    val title:String,
    val releaseDate:String,
    val image:String,
    val plot:String,
    val stars:List<String>
)
