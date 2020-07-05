package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.usecase.GetAllCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val getAllCharactersUseCase: GetAllCharactersUseCase) : ViewModel() {

    fun getAllCharacters(): LiveData<NetworkResultState<List<CharacterModel>>> {
        return getAllCharactersUseCase.execute(Unit).asLiveData()
    }
}