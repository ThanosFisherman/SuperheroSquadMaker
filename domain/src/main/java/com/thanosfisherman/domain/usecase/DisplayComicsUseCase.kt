package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.BaseUseCase
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.flow.Flow

class DisplayComicsUseCase(private val networkRepo: NetworkRepo) : BaseUseCase<Long, Flow<NetworkResultState<List<ComicModel>>>>() {
    override fun execute(params: Long): Flow<NetworkResultState<List<ComicModel>>> {
        return networkRepo.getAllComicsByCharacterId(params)
    }
}