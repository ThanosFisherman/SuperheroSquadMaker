package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.BaseUseCase
import com.thanosfisherman.domain.common.UseCaseResult
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class GetAllCharactersUseCase(private val networkRepo: NetworkRepo) : BaseUseCase<Unit, Flow<UseCaseResult<List<CharacterModel>>>>() {

    override fun execute(params: Unit): Flow<UseCaseResult<List<CharacterModel>>> {
        return networkRepo.getAllCharacters().onStart { emit(UseCaseResult.Loading) }
    }
}