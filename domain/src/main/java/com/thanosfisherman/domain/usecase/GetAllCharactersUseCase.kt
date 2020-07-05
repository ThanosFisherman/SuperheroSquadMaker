package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.BaseUseCase
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class GetAllCharactersUseCase(private val networkRepo: NetworkRepo) : BaseUseCase<Unit, Flow<NetworkResultState<List<CharacterModel>>>>() {

    override fun execute(params: Unit): Flow<NetworkResultState<List<CharacterModel>>> {
        return networkRepo.getAllCharacters().onStart { emit(NetworkResultState.Loading) }
    }
}