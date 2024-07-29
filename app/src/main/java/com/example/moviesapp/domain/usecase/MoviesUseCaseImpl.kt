package com.example.moviesapp.domain.usecase

import com.example.moviesapp.common.AppConfig
import com.example.moviesapp.common.service.Resource
import com.example.moviesapp.domain.data.MoviesResponse
import com.example.moviesapp.domain.repo.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Locale

class MoviesUseCaseImpl(
    private val repository: MoviesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : MoviesUseCase {
    override suspend fun getMovies(): Flow<Resource<MoviesResponse>> {
        return flow {
            repository.fetchMovies().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val baseUrl = AppConfig.PHOTO_LIST_BASE_URL
                        val modifiedMovies = resource.data?.movies?.map { movie ->
                            movie.copy(
                                url = "$baseUrl${movie.url}",
                                voteAverage = String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    movie.voteAverage
                                ).toDouble()
                            )
                        }
                        emit(Resource.Success(MoviesResponse(modifiedMovies)))
                    }

                    else -> {
                        emit(resource)
                    }
                }
            }
        }.flowOn(dispatcher)

    }
}

interface MoviesUseCase {
    suspend fun getMovies(): Flow<Resource<MoviesResponse>>
}