package com.example.moviesapp.domain

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MoviesResponse(
    @SerializedName("results")
    var movies: List<MovieItemResponse>? = null
)

data class MovieItemResponse(
    @SerializedName("id")
    val id : String? = null,
    @SerializedName("poster_path")
    var url: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null
) {

}