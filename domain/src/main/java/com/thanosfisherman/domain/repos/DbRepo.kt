package com.thanosfisherman.domain.repos

import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface DbRepo {

    fun getAllHeroes(): Flow<DbResultState<List<CharacterModel>>>

    fun getSquad(): Flow<DbResultState<List<CharacterModel>>>

    fun addHeroToSquad(characterModel: CharacterModel): Flow<DbResultState<Long>>

    fun deleteHeroFromSquad(characterModel: CharacterModel): Flow<DbResultState<Unit>>

    fun addHero(characterModel: CharacterModel): Flow<DbResultState<Long>>

    fun addAllHeroes(heroes: List<CharacterModel>): Flow<DbResultState<Unit>>

    fun updateSquad(characterModel: CharacterModel): Flow<DbResultState<Int>>
}