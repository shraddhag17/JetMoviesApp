package com.example.moviesapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.common.service.Resource
import com.example.moviesapp.common.service.RetrofitService
import com.example.moviesapp.common.ui.UIState
import com.example.moviesapp.common.utils.DateUtils
import com.example.moviesapp.domain.usecase.MovieDetailUseCaseImpl
import com.example.moviesapp.domain.data.MovieDetailsResponse
import com.example.moviesapp.domain.repo.MoviesRepository
import com.example.moviesapp.ui.model.MovieDetailModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MoviesDetailViewModel(val useCase: MovieDetailUseCaseImpl) : ViewModel() {

    private var _uiState: MutableStateFlow<UIState<MovieDetailModel>> =
        MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<MovieDetailModel>> = _uiState.asStateFlow()


    fun fetchMovieDetails(id: String?) {
        viewModelScope.launch {
            if (id.isNullOrBlank()) UIState.Error("Error loading movies. Please try again later")
            useCase.getMovieDetail(id!!).catch { exception ->
                _uiState.value = UIState.Error("Something went wrong, Please try again later")
            }.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val uiCards = getUICards(resource.data)
                        _uiState.value =
                            if (uiCards == null) UIState.Error("Error loading movies. Please try again later") else UIState.Success(
                                uiCards
                            )
                    }

                    is Resource.Loading -> {
                        _uiState.value = UIState.Loading
                    }

                    else -> {
                        _uiState.value = UIState.Error(resource.message ?: "No movies to display.")
                    }
                }

            }
        }
    }


    fun getUICards(response: MovieDetailsResponse?): MovieDetailModel? {
        response?.let {
            if (!it.title.isNullOrBlank())
                return MovieDetailModel(
                    it.url, it.title, it.overview, it.runtime,
                    DateUtils.convertDateToString(response.date), it.voteAverage.toString()
                )
        }
        return null
    }

    companion object {
        fun provideFactory(
            context: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = MoviesRepository(RetrofitService.getApiService(context))
                return MoviesDetailViewModel(MovieDetailUseCaseImpl(repository)) as T
            }
        }
    }

}