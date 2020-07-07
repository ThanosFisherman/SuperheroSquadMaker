package com.thanosfisherman.data_network.models.response.characters

import com.thanosfisherman.domain.common.DomainMappable
import com.thanosfisherman.domain.model.CharacterModel

data class CharactersDataModel(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: Data,
    val etag: String,
    val status: String
) : DomainMappable<List<CharacterModel>> {
    override fun asDomain(): List<CharacterModel> {
        val mutableList = mutableListOf<CharacterModel>()
        data.results.forEach {
            mutableList.add(
                CharacterModel(
                    it.id, it.name, it.description, (it.thumbnail.path + "." + it.thumbnail.extension)
                )
            )
        }
        return mutableList
    }
}