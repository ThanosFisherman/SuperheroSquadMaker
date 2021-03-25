package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.thanosfisherman.domain.usecase.GetAllCharactersApiUseCase
import com.thanosfisherman.domain.usecase.GetSquadUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(
    private val getAllCharactersApiUseCase: GetAllCharactersApiUseCase,
    private val getSquadUseCase: GetSquadUseCase
) : ViewModel() {

    //val liveNetworkResult = heroesPageDataSource.networkStateChannel.asFlow().asLiveData()
    val liveCharactersPagedApi = getAllCharactersApiUseCase.execute(2).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    val liveSquadCharacters = getSquadUseCase.channelSquad.asFlow().asLiveData()

    fun getSquad() {
        viewModelScope.launch(Dispatchers.IO) {
            getSquadUseCase.getSquad()
        }
    }
}