package com.example.moviesapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.common.service.Resource
import com.example.moviesapp.common.service.RetrofitService
import com.example.moviesapp.common.ui.UIState
import com.example.moviesapp.domain.data.MovieItemResponse
import com.example.moviesapp.domain.repo.MoviesRepository
import com.example.moviesapp.domain.usecase.MoviesUseCaseImpl
import com.example.moviesapp.ui.model.MovieItemCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MoviesViewModel(private val useCase: MoviesUseCaseImpl) :
    ViewModel() {

    private var _uiState: MutableStateFlow<UIState<List<MovieItemCard>>> =
        MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<List<MovieItemCard>>> = _uiState.asStateFlow()

    fun fetchMovies() {
        viewModelScope.launch {
            useCase.getMovies().catch { exception ->
                _uiState.value = UIState.Error("Something went wrong, Please try again later")
            }.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val uiCards = getUICards(resource.data?.movies)
                        _uiState.value =
                            if (uiCards.isEmpty()) UIState.Error("No movies found. Please try again later") else UIState.Success(
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


    fun getUICards(response: List<MovieItemResponse>?): List<MovieItemCard> {
        val cards: MutableList<MovieItemCard> = ArrayList()
        response?.forEach {
            if (!it.title.isNullOrBlank())
                cards.add(
                    MovieItemCard(
                        it.id ?: "",
                        it.title.trim(),
                        it.url,
                        it.voteAverage.toString()
                    )
                )
        }
        return cards
    }

    companion object {
        fun provideFactory(
            context: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = MoviesRepository(RetrofitService.getApiService(context))
                return MoviesViewModel(MoviesUseCaseImpl(repository)) as T
            }
        }
    }
}