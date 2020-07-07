package com.thanosfisherman.data_network.models.response.comics


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TextObject(
    @Json(name = "language")
    val language: String,
    @Json(name = "text")
    val text: String,
    @Json(name = "type")
    val type: String
)