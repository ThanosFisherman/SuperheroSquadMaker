package com.thanosfisherman.domain.repos

import com.thanosfisherman.domain.common.UseCaseResult
import com.thanosfisherman.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface NetworkRepo {

    fun getAllCharacters(): Flow<UseCaseResult<List<CharacterModel>>>
}