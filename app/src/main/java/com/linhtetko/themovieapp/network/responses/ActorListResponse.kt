package com.linhtetko.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.linhtetko.themovieapp.data.vos.ActorVO

data class ActorListResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val result: List<ActorVO>
)