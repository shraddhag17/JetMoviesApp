package com.example.moviesapp.common.service

import com.example.moviesapp.common.utils.TokenUtils
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        requestBuilder
            .header(HeaderKeys.AUTHORIZATION, TokenUtils.getToken())
            .header(HeaderKeys.ACCEPT, HeaderValues.CONTENT_TYPE_VALUE)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }

}
