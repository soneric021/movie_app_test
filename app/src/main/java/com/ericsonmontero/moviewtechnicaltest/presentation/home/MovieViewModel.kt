package com.ericsonmontero.moviewtechnicaltest.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel
import com.ericsonmontero.moviewtechnicaltest.domain.models.Resource
import com.ericsonmontero.moviewtechnicaltest.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {

    private val _movies:MutableStateFlow<UiState> = MutableStateFlow(UiState.Success(emptyList()))
    val movies:StateFlow<UiState>
     get() = _movies

    init {
        getMovies()
    }

    fun getMovies(){
        viewModelScope.launch {
            _movies.emit(UiState.ShowProgress(true))
            getMoviesUseCase.invoke(true).collect {
                when(it){
                    is Resource.Error -> {
                        _movies.emit(UiState.ShowProgress(false))
                        _movies.emit(UiState.Error(it.exception))
                    }
                    is Resource.Success -> {
                        _movies.emit(UiState.ShowProgress(false))
                        _movies.emit(UiState.Success(it.data ?: emptyList()))
                    }
                }
            }
        }
    }
}

sealed class UiState {
    data class Success(val news: List<MovieModel>): UiState()
    data class Error(val exception: String): UiState()
    data class ShowProgress(val isShowing:Boolean) : UiState()
}