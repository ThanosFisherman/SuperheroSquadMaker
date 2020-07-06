package com.thanosfisherman.domain.repos

import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface DbRepo {

    fun getAllHeroes(): Flow<DbResultState<List<CharacterModel>>>

    fun getSquad(): Flow<DbResultState<List<CharacterModel>>>

    suspend fun addHeroToSquad(characterModel: CharacterModel): Flow<DbResultState<Long>>

    suspend fun deleteHeroFromSquad(characterModel: CharacterModel): Flow<DbResultState<Unit>>

    suspend fun addHero(characterModel: CharacterModel): Flow<DbResultState<Long>>

    suspend fun addAllHeroes(heroes: List<CharacterModel>)

    suspend fun updateSquad(characterModel: CharacterModel): Flow<DbResultState<Int>>
}