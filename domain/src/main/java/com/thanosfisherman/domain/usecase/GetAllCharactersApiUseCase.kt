package com.thanosfisherman.domain.usecase

import androidx.paging.PagingData
import com.thanosfisherman.domain.common.BaseUseCase
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class GetAllCharactersApiUseCase(private val networkRepo: NetworkRepo) : BaseUseCase<Int, Flow<PagingData<CharacterModel>>>() {
    override fun execute(params: Int): Flow<PagingData<CharacterModel>> {
        return networkRepo.getAllCharacters(params)
    }
}