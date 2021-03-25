package com.thanosfisherman.data_network.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thanosfisherman.data_network.api.MarvelApi
import com.thanosfisherman.data_network.common.mapToDomain
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class NetworkRepoImpl(private val marvelApi: MarvelApi) : NetworkRepo {
    override fun getAllCharacters(offset: Int): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig(50, enablePlaceholders = true),
            pagingSourceFactory = { HeroesPageDataSource(marvelApi) }
        ).flow
    }

    override fun getAllComicsByCharacterId(charId: Long): Flow<NetworkResultState<List<ComicModel>>> =
        flow {
            emit(marvelApi.getComicsByCharId(charId).mapToDomain())
        }.flowOn(Dispatchers.IO)
}