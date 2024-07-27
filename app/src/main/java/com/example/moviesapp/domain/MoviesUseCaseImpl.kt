package com.example.moviesapp.domain

import com.example.moviesapp.common.service.Resource
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
                        val baseUrl = "https://media.themoviedb.org/t/p/w100_and_h100_face"
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