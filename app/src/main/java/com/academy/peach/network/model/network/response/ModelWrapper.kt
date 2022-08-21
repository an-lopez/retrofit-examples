package com.academy.peach.network.model.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelWrapper(
    @field:Json(name = "info") val info: ModelInfo,
    @field:Json(name = "results") val results: List<Character>
)
