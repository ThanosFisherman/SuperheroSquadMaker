package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.thanosfisherman.domain.common.HeroesPageDataSourceFactory
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.DbRepo
import com.thanosfisherman.domain.repos.NetworkRepo
import com.thanosfisherman.presentation.common.utils.singleSourceLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val dbRepo: DbRepo, private val networkRepo: NetworkRepo) : ViewModel() {
    val heroesLive = singleSourceLiveData(viewModelScope,
        databaseQuery = { dbRepo.getAllHeroes() },
        networkCall = { networkRepo.getAllCharacters(2) },
        saveCallResult = { characters -> dbRepo.addAllHeroes(characters) }
    )
    val squadLive = dbRepo.getSquad().asLiveData()

    fun observeRemotePagedSets(): LiveData<PagedList<CharacterModel>> {
        val dataSourceFactory = HeroesPageDataSourceFactory(networkRepo, viewModelScope)
        return LivePagedListBuilder(dataSourceFactory, HeroesPageDataSourceFactory.pagedListConfig()).build()
    }
}