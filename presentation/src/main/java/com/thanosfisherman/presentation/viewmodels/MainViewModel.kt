package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.thanosfisherman.domain.common.HeroesPageDataSource
import com.thanosfisherman.domain.common.HeroesPageDataSourceFactory
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.usecase.GetAllCharactersApiUseCase
import com.thanosfisherman.domain.usecase.GetSquadUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(
    private val getAllCharactersApiUseCase: GetAllCharactersApiUseCase,
    private val getSquadUseCase: GetSquadUseCase
) : ViewModel() {

    private val heroesPageDataSource: HeroesPageDataSource = HeroesPageDataSource(getAllCharactersApiUseCase, viewModelScope)

    val liveNetworkResult = heroesPageDataSource.networkStateChannel.asFlow().asLiveData()

    val liveCharactersPagedApi: LiveData<PagedList<CharacterModel>> = kotlin.run {
        val dataSourceFactory = HeroesPageDataSourceFactory(heroesPageDataSource, getAllCharactersApiUseCase, viewModelScope)
        LivePagedListBuilder(dataSourceFactory, HeroesPageDataSourceFactory.pagedListConfig()).build()
    }

    val liveSquadCharacters = getSquadUseCase.channelSquad.asFlow().asLiveData()

    fun getSquad() {
        viewModelScope.launch(Dispatchers.IO) {
            getSquadUseCase.getSquad()
        }
    }
}