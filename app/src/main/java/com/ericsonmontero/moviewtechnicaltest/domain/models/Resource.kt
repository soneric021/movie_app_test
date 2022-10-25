package com.ericsonmontero.moviewtechnicaltest.domain.models

sealed class Resource<out R> {

    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val exception: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}
