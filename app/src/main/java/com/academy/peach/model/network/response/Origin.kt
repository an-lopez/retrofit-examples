package com.academy.peach.model.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Origin(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)