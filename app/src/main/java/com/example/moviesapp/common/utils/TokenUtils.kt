package com.example.moviesapp.common.utils

import com.example.moviesapp.common.EncryptedSharedPref
import com.example.moviesapp.common.HeaderValues
import com.example.moviesapp.common.PrefKeys

object TokenUtils {

    fun getToken(): String {
        try {
            val builder = StringBuilder()
            builder.append(HeaderValues.TOKEN_TYPE).append(" ")
            val token = EncryptedSharedPref.getString(PrefKeys.ACCESS_TOKEN)
            builder.append(token)

            return builder.toString()
        } catch (exception: Exception) {
            //Report to firebase - This should never happen
        }
        return ""
    }
}