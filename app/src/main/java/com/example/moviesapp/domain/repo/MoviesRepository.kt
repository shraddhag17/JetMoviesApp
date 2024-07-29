package com.example.moviesapp.domain.repo

import com.example.moviesapp.common.service.BaseRepository
import com.example.moviesapp.common.service.Resource
import com.example.moviesapp.domain.data.MoviesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MoviesRepository(
    private val apiService: MoviesApiService,
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(dispatchers){

    suspend fun fetchMovies() : Flow<Resource<MoviesResponse>> {
       return flow {
           emit(Resource.Loading)
           val resource = safeApiCall {
            apiService.getMovies()
           }
           emit(resource)
       }.flowOn(dispatchers)
    }

    suspend fun getMovieDetails(id : String) = flow {
        emit(Resource.Loading)
        val resource = safeApiCall {
            apiService.getMovieDetails(id)
        }
        emit(resource)
    }.flowOn(dispatchers)
}