package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.DbRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel

@ExperimentalCoroutinesApi
class GetSquadUseCase(private val dbRepo: DbRepo) {

    val channelSquad: BroadcastChannel<DbResultState<List<CharacterModel>>> = BroadcastChannel(Channel.BUFFERED)

    suspend fun getSquad() {
        when (val squadResult = dbRepo.getSquad()) {
            is DbResultState.Success -> {
                channelSquad.offer(DbResultState.Success(squadResult.data))
            }
            else -> channelSquad.offer(squadResult)
        }
    }
}