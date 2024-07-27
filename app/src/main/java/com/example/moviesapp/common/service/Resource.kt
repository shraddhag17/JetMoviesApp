package com.example.moviesapp.common.service

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<out T>(data: T) : Resource<T>(data = data)

    class Error(errorMessage: String) : Resource<Nothing>(message = errorMessage)

    object Loading : Resource<Nothing>()
}
