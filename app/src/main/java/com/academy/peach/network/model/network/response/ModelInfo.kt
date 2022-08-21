package com.academy.peach.network.model.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelInfo(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "pages") val pages: Int,
    @field:Json(name = "next") val next: String?,
    @field:Json(name = "prev") val previous: String?
)
