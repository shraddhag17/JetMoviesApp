package com.example.moviesapp.common.service

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException


abstract class BaseRepository(private val ioDispatcher: CoroutineDispatcher) {
    // we'll use this function in all repos to handle api errors.
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(ioDispatcher) {
            try {
                // Here we are calling api lambda function that will return response wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    try {
                        Resource.Success(data = response.body()!!)
                    } catch (exception: Exception) {
                        Resource.Error(errorMessage = "Error loading data. Please try again later")
                    }
                } else {
                    //In actual scenario, we need to map error messaging here, based on status code form server.
                    Resource.Error(
                        errorMessage = "Error loading data. Please try again later"
                    )
                }

            } catch (exception: NoConnectivityException) {
                Resource.Error(errorMessage = exception.message)
            } catch (exception: IOException) {
                Resource.Error("Connection Error. Unable to connect to server, please check your internet connection.")
            } catch (exception: Exception) {
                Resource.Error(errorMessage = "Something went wrong.Please try again later")
            }
        }
    }
}
