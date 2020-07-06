package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanosfisherman.domain.repos.DbRepo
import com.thanosfisherman.domain.repos.NetworkRepo
import com.thanosfisherman.presentation.common.utils.resultLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val dbRepo: DbRepo, networkRepo: NetworkRepo) : ViewModel() {
    val heroesLive = resultLiveData(viewModelScope,
        databaseQuery = { dbRepo.getAllHeroes() },
        networkCall = { networkRepo.getAllCharacters() },
        saveCallResult = { characters -> dbRepo.addAllHeroes(characters) }
    )
}