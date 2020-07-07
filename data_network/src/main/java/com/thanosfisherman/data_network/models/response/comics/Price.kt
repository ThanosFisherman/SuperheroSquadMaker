package com.thanosfisherman.data_network.models.response.comics


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
    @Json(name = "price")
    val price: Double,
    @Json(name = "type")
    val type: String
)