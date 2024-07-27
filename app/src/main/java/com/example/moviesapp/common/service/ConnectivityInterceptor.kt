package com.example.moviesapp.common.service

import android.content.Context
import com.example.moviesapp.common.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!NetworkUtils.isInternetConnectionAvailable(context)) {
             throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }
}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No internet connection. Please try again later"
}