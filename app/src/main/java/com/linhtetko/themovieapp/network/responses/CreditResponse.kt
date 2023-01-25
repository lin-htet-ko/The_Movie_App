package com.linhtetko.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.linhtetko.themovieapp.data.vos.ActorVO

data class CreditResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: List<ActorVO>?,
    @SerializedName("crew")
    val crew: List<ActorVO>?,
)
