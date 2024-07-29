package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.common.service.PrefKeys
import com.example.moviesapp.common.storage.EncryptedSharedPref

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        EncryptedSharedPref.init(this)
        val token = EncryptedSharedPref.getString(PrefKeys.ACCESS_TOKEN)
        if(token.isNullOrBlank()) {
            //Usually we save to preference when we get token from API. Hardcoding here for sample exercise
            EncryptedSharedPref.saveString(PrefKeys.ACCESS_TOKEN,
                "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NzQwNGZjZTQ5MmM1NzIyYTA2MzE3NWI4MTY3NjE1YiIsIm5iZiI6MTcyMTk2MDMxMi4xMjAxNzUsInN1YiI6IjY2YTFiNWIwMGEzZjliYzU2MWRkOTQ3MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ACOJBAgwAPZCN53MNx1RcvLgUxIKV-ZcJg06uoz3HWQ")
        }
    }
}