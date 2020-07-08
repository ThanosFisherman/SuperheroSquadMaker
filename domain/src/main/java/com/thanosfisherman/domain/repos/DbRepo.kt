package com.thanosfisherman.domain.repos

import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface DbRepo {

    fun getAllHeroes(): Flow<DbResultState<List<CharacterModel>>>

    suspend fun getSquad(): DbResultState<List<CharacterModel>>

    suspend fun addHeroToSquad(characterModel: CharacterModel)

    suspend fun deleteHeroFromSquad(characterModel: CharacterModel)

    fun addHero(characterModel: CharacterModel): Flow<DbResultState<Long>>

    fun addAllHeroes(heroes: List<CharacterModel>): Flow<DbResultState<Unit>>

    fun updateSquad(characterModel: CharacterModel): Flow<DbResultState<Int>>

    suspend fun checkIsHeroInSquad(characterModel: CharacterModel): DbResultState<Boolean>
}