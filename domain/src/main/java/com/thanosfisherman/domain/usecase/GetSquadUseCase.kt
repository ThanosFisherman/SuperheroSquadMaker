package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.BaseUseCase
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.DbRepo
import kotlinx.coroutines.flow.Flow

class GetSquadUseCase(private val dbRepo: DbRepo) : BaseUseCase<Unit, Flow<DbResultState<List<CharacterModel>>>>() {
    override fun execute(params: Unit): Flow<DbResultState<List<CharacterModel>>> {
        return dbRepo.getSquad()
    }
}