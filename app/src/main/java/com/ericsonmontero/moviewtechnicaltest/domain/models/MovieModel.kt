package com.ericsonmontero.moviewtechnicaltest.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieModel(
    val title:String,
    val releaseDate:String,
    val image:String,
    val plot:String,
    val stars:List<String>
) : Parcelable
