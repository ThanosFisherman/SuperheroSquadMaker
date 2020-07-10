package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.domain.usecase.DisplayComicsUseCase
import com.thanosfisherman.domain.usecase.SquadManagerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class HeroDetailsViewModel(
    private val displayComicsUseCase: DisplayComicsUseCase,
    private val squadManagerUseCase: SquadManagerUseCase
) : ViewModel() {

    val liveAdRemove = squadManagerUseCase.channelAddRemove.asFlow().asLiveData()
    val liveCheckInSquad = squadManagerUseCase.channelCheck.asFlow().asLiveData()

    fun liveGetComicByCharId(charId: Long): LiveData<NetworkResultState<List<ComicModel>>> {
        return displayComicsUseCase.execute(charId).asLiveData()
    }

    fun checkIsInSquad(characterModel: CharacterModel?) {
        viewModelScope.launch(Dispatchers.IO) { squadManagerUseCase.checkIsHeroInSquad(characterModel) }
    }

    fun addOrRemoveFromSquad(characterModel: CharacterModel?, isPromptShown: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) { squadManagerUseCase.addRemove(characterModel, isPromptShown) }
    }
}