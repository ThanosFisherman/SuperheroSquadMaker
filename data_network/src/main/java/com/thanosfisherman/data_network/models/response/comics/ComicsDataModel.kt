package com.thanosfisherman.data_network.models.response.comics

import com.thanosfisherman.domain.common.DomainMappable
import com.thanosfisherman.domain.model.ComicModel

data class ComicsDataModel(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val etag: String,
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
                    it.description ?: "",
                    (it.thumbnail.path + "." + it.thumbnail.extension)
                )
            )
        }
        return mutableList
    }
}