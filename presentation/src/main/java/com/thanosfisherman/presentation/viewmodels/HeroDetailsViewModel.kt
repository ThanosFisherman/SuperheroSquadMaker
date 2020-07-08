package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.domain.usecase.AddToSquadUseCase
import com.thanosfisherman.domain.usecase.DeleteFromSquadUseCase
import com.thanosfisherman.domain.usecase.DisplayComicsUseCase

class HeroDetailsViewModel(
    private val displayComicsUseCase: DisplayComicsUseCase,
    private val addToSquadUseCase: AddToSquadUseCase,
    private val deleteFromSquadUseCase: DeleteFromSquadUseCase
) : ViewModel() {

    fun liveGetComicByCharId(charId: Long): LiveData<NetworkResultState<List<ComicModel>>> {
        return displayComicsUseCase.execute(charId).asLiveData()
    }
    fun liveAddToSquad() {

    }
    fun liveDeleteFromSquad() {

    }

    fun liveCheckInSquad() {

    }
}