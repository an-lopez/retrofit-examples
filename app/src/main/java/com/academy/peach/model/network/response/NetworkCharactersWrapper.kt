package com.academy.peach.model.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkCharactersWrapper(
    @Json(name = "info")
    val info: Info,
    @Json(name = "results")
    val characters: List<NetworkCharacter>
)