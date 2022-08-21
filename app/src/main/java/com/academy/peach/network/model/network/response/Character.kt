package com.academy.peach.network.model.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "image") val image: String
)
