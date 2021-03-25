package com.thanosfisherman.domain.repos

import androidx.paging.PagingData
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ComicModel
import kotlinx.coroutines.flow.Flow

interface NetworkRepo {

    fun getAllCharacters(offset: Int): Flow<PagingData<CharacterModel>>
    fun getAllComicsByCharacterId(charId: Long): Flow<NetworkResultState<List<ComicModel>>>
}