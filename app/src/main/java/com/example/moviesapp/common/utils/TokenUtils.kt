package com.example.moviesapp.common.utils

import android.util.Log
import com.example.moviesapp.common.service.HeaderValues
import com.example.moviesapp.common.service.PrefKeys
import com.example.moviesapp.common.storage.EncryptedSharedPref

object TokenUtils {

    fun getToken(): String {
        try {
            val builder = StringBuilder()
            builder.append(HeaderValues.TOKEN_TYPE).append(" ")
            val token = EncryptedSharedPref.getString(PrefKeys.ACCESS_TOKEN)
            builder.append(token)

            return builder.toString()
        } catch (exception: Exception) {
            Log.d("Token Exception", "Token exception "+ exception)
            //Report to firebase - This should never happen
        }
        return ""
    }
}