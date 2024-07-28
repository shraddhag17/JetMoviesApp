package com.example.moviesapp.domain

import com.example.moviesapp.common.service.Resource
import com.example.moviesapp.common.utils.DateUtils
import com.example.moviesapp.common.utils.UiUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale

class MovieDetailUseCaseImpl(
    private val repository: MoviesRepository,
    private val dispatchers: CoroutineDispatcher = Dispatchers.Default
) : MovieDetailUseCase {
    override suspend fun getMovieDetail(id: String): Flow<Resource<MovieDetailsResponse>> {
        return flow {
            repository.getMovieDetails(id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val baseUrl = "https://media.themoviedb.org/t/p/w220_and_h330_face"
                        val model = resource.data?.copy(
                            url = "$baseUrl${resource.data.url}",
                            voteAverage = String.format(
                                Locale.getDefault(),
                                "%.2f",
                                resource.data.voteAverage
                            ).toDouble(),
                            runtime = UiUtils.formatDuration(resource.data.runtime)
                        )
                        model?.let {
                            model.date = DateUtils.convertStringToDate(model.releaseDate)
                            emit(Resource.Success(model))
                        } ?: run {
                            emit(Resource.Error("Error loading data. Please try again later"))
                        }
                    }

                    else -> {
                        emit(resource)
                    }
                }
            }
        }.flowOn(dispatchers)
    }


}

interface MovieDetailUseCase {
    suspend fun getMovieDetail(id: String): Flow<Resource<MovieDetailsResponse>>
}