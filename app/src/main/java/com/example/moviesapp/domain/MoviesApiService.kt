package com.example.moviesapp.domain

import com.example.moviesapp.common.service.BaseResponse
import com.example.moviesapp.common.service.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/popular")
    suspend fun getMovies(/*@Query("language") language: String = "en-US"*/) : Response<MoviesResponse>

    @GET("movie/{MOVIE_ID}")
    suspend fun getMovieDetails(
        @Path("MOVIE_ID") id: String,
        @Query("language") language: String = "en-US"
    ) : Response<MovieDetailsResponse>
}