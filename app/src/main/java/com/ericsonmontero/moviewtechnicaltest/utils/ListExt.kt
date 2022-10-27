package com.ericsonmontero.moviewtechnicaltest.utils

import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel

fun List<MovieModel>.orderByDateDescending(ordered:Boolean) : List<MovieModel> {
    return if(ordered){
        this.sortedByDescending { Utils.parseDate(it.releaseDate) }
    }else{
        this
    }
}