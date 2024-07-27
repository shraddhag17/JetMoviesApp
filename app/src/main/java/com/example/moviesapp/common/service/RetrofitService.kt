package com.example.moviesapp.common.service

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitService {

    private var retrofit: Retrofit? = null
    private fun getRetrofitInstance(context: Context): Retrofit {
        // Client
        val client = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .addInterceptor(ConnectivityInterceptor(context))
            .addInterceptor(HeaderInterceptor())
            .build()

        // Retrofit
        return Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    fun getBaseApiService(context: Context): BaseApiService? {
        if (retrofit == null) {
            retrofit = getRetrofitInstance(context)
        }
        return retrofit?.create(BaseApiService::class.java)
    }

    fun getApiService(context: Context): BaseApiService {
        if (retrofit == null) {
            retrofit = getRetrofitInstance(context)
        }
        return retrofit!!.create(BaseApiService::class.java)
    }
}
