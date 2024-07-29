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
    var overview: String? = null,
    @SerializedName("runtime")
    var runtime: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null
) {
    var date: Date? = null
}