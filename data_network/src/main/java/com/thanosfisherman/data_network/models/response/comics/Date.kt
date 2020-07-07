package com.thanosfisherman.data_network.models.response.comics


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Date(
    @Json(name = "date")
    val date: String,
    @Json(name = "type")
    val type: String
)