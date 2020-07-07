package com.thanosfisherman.data_network.models.response.comics


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "characters")
    val characters: Characters,
    @Json(name = "collectedIssues")
    val collectedIssues: List<Any>,
    @Json(name = "collections")
    val collections: List<Any>,
    @Json(name = "creators")
    val creators: Creators,
    @Json(name = "dates")
    val dates: List<Date>,
    @Json(name = "description")
    val description: String,
    @Json(name = "diamondCode")
    val diamondCode: String,
    @Json(name = "digitalId")
    val digitalId: Int,
    @Json(name = "ean")
    val ean: String,
    @Json(name = "events")
    val events: Events,
    @Json(name = "format")
    val format: String,
    @Json(name = "id")
    val id: Long,
    @Json(name = "images")
    val images: List<Image>,
    @Json(name = "isbn")
    val isbn: String,
    @Json(name = "issn")
    val issn: String,
    @Json(name = "issueNumber")
    val issueNumber: Int,
    @Json(name = "modified")
    val modified: String,
    @Json(name = "pageCount")
    val pageCount: Int,
    @Json(name = "prices")
    val prices: List<Price>,
    @Json(name = "resourceURI")
    val resourceURI: String,
    @Json(name = "series")
    val series: Series,
    @Json(name = "stories")
    val stories: Stories,
    @Json(name = "textObjects")
    val textObjects: List<TextObject>,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail,
    @Json(name = "title")
    val title: String,
    @Json(name = "upc")
    val upc: String,
    @Json(name = "urls")
    val urls: List<Url>,
    @Json(name = "variantDescription")
    val variantDescription: String,
    @Json(name = "variants")
    val variants: List<Any>
)