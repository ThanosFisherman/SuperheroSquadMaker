package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.BaseUseCase
import com.thanosfisherman.domain.common.UseCaseResult
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllCharactersUseCase(private val networkRepo: NetworkRepo) : BaseUseCase<Unit, Flow<UseCaseResult<List<CharacterModel>>>>() {
    override fun execute(params: Unit): Flow<UseCaseResult<List<CharacterModel>>> {
        return networkRepo.getAllCharacters().map {
            when (it) {
                is UseCaseResult.Success -> UseCaseResult.Success(it.data)
                is UseCaseResult.Error -> UseCaseResult.Error(it.error)
                is UseCaseResult.Loading -> it
            }
        }
    }
}