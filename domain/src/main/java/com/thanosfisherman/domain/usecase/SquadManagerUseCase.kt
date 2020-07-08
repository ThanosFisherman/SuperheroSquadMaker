package com.thanosfisherman.domain.usecase

import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.DbRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import timber.log.Timber

@ExperimentalCoroutinesApi
class SquadManagerUseCase(private val dbRepo: DbRepo) {
    val channelAddRemove: BroadcastChannel<DbResultState<Boolean>> = BroadcastChannel(Channel.BUFFERED)
    val channelCheck: BroadcastChannel<DbResultState<Boolean>> = BroadcastChannel(Channel.BUFFERED)

    suspend fun checkIsHeroInSquad(characterModel: CharacterModel?) {
        if (characterModel == null)
            return
        when (val checkResult = dbRepo.checkIsHeroInSquad(characterModel)) {
            is DbResultState.Success -> {
                channelCheck.offer(checkResult)
            }
            else -> Timber.i("ERROR CHECKING")
        }
    }

    suspend fun addRemove(characterModel: CharacterModel?, isPromptShown: Boolean = false) {
        if (characterModel == null) {
            channelAddRemove.offer(DbResultState.GenericError)
            return
        }
        when (val checkResult = dbRepo.checkIsHeroInSquad(characterModel)) {
            is DbResultState.Success -> {
                if (checkResult.data) {
                    if (!isPromptShown) {
                        channelAddRemove.offer(DbResultState.ShowDialodRemove)
                    } else {
                        dbRepo.deleteHeroFromSquad(characterModel)
                        channelAddRemove.offer(DbResultState.Success(false))
                    }
                } else {
                    dbRepo.addHeroToSquad(characterModel)
                    channelAddRemove.offer(DbResultState.Success(true))
                }
            }
        }
    }
}