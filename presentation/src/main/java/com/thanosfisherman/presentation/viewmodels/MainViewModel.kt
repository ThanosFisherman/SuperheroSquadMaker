package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thanosfisherman.domain.common.UseCaseResult
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.usecase.GetAllCharactersUseCase

class MainViewModel(private val getAllCharactersUseCase: GetAllCharactersUseCase) : ViewModel() {

    fun getAllCharacters(): LiveData<UseCaseResult<List<CharacterModel>>> {
        return getAllCharactersUseCase.execute(Unit).asLiveData()
    }
}