package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.BaseUseCase
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.DbRepo
import kotlinx.coroutines.flow.Flow

class DeleteFromSquadUseCase(private val dbRepo: DbRepo) : BaseUseCase<CharacterModel, Flow<DbResultState<Unit>>>() {
    override fun execute(params: CharacterModel): Flow<DbResultState<Unit>> {
        return dbRepo.deleteHeroFromSquad(params)
    }
}