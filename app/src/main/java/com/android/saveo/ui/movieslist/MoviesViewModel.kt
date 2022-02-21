package com.android.saveo.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.saveo.repository.MoviesRepository
import com.android.saveo.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
):ViewModel() {
    private val _uiState = MutableStateFlow<MoviesStateEvent>(
        MoviesStateEvent.Empty
    )

    val uiState: StateFlow<MoviesStateEvent> = _uiState

    fun getMoviesListData(paget:Int,limt:Int){
        viewModelScope.launch {
            moviesRepository.getMoviesData(paget,limt).onEach {
                when(it){
                    is DataState.Success ->{
                        val data = MoviesStateEvent.MoviesSuccess(it.data)
                        _uiState.value = data
                    }
                    is DataState.Error ->{
                        _uiState.value = MoviesStateEvent.Error(it)
                    }
                    is DataState.HideLoading ->{
                        _uiState.value = MoviesStateEvent.HideProgress
                    }
                    is DataState.ShowLoading ->{
                        _uiState.value = MoviesStateEvent.HideProgress
                    }
                    else -> {
                        _uiState.value = MoviesStateEvent.Empty
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}