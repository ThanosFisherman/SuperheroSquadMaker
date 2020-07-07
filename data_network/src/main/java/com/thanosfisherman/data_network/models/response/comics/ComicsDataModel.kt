package com.thanosfisherman.data_network.models.response.comics


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.thanosfisherman.domain.common.DomainMappable
import com.thanosfisherman.domain.model.ComicModel

@JsonClass(generateAdapter = true)
data class ComicsDataModel(
    @Json(name = "attributionHTML")
    val attributionHTML: String,
    @Json(name = "attributionText")
    val attributionText: String,
    @Json(name = "code")
    val code: Int,
    @Json(name = "copyright")
    val copyright: String,
    @Json(name = "data")
    val data: Data,
    @Json(name = "etag")
    val etag: String,
    @Json(name = "status")
    val status: String
) : DomainMappable<List<ComicModel>> {
    override fun asDomain(): List<ComicModel> {
        val mutableList = mutableListOf<ComicModel>()
        data.results.forEach {
            mutableList.add(
                ComicModel(
                    it.id,
                    it.title,
                    it.issueNumber,
                    it.description,
                    (it.thumbnail.path + "." + it.thumbnail.extension)
                )
            )
        }
        return mutableList
    }
}