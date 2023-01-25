package com.linhtetko.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.linhtetko.themovieapp.data.vos.GenreVO

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<GenreVO>
)
