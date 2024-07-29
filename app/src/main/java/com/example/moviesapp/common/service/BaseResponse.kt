package com.example.moviesapp.common.service

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("success") val success: Boolean? = null,

    @SerializedName("status_message") val message: String? = null,
    val data: T? = null
)