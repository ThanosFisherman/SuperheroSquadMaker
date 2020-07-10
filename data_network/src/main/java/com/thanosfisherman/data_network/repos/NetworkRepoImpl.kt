package com.thanosfisherman.data_network.repos

import com.thanosfisherman.data_network.api.MarvelApi
import com.thanosfisherman.data_network.common.mapToDomain
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkRepoImpl(private val marvelApi: MarvelApi) : NetworkRepo {
    override fun getAllCharacters(offset: Int): Flow<NetworkResultState<List<CharacterModel>>> = flow {
        emit(marvelApi.getCharacters(offset).mapToDomain())
    }.flowOn(Dispatchers.IO)

    override fun getAllComicsByCharacterId(charId: Long): Flow<NetworkResultState<List<ComicModel>>> = flow {
        emit(marvelApi.getComicsByCharId(charId).mapToDomain())
    }.flowOn(Dispatchers.IO)
}