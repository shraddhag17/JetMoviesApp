package com.example.moviesapp.common

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object EncryptedSharedPref {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {

        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sharedPreferences = EncryptedSharedPreferences.create(
            "MoviesApp",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveString(key : String, token: String) {
        sharedPreferences.edit().putString(key, token).apply()
    }

    fun getString(key : String): String? {
        return sharedPreferences.getString(key, null)
    }
}