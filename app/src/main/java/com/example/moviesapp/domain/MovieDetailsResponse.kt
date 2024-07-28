package com.example.moviesapp.domain

import com.google.gson.annotations.SerializedName
import java.util.Date

data class MovieDetailsResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("poster_path")
    var url: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("runtime")
    var runtime: String?,
    @SerializedName("release_date")
    var releaseDate: String?
) {
    var date : Date? = null
}