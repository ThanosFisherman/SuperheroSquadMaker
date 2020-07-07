package com.thanosfisherman.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.domain.repos.DbRepo
import com.thanosfisherman.domain.repos.NetworkRepo

class HeroDetailsViewModel(private val dbRepo: DbRepo, private val networkRepo: NetworkRepo) : ViewModel() {

    fun getComicByCharId(charId: Long): LiveData<NetworkResultState<List<ComicModel>>> {
        return networkRepo.getAllComicsByCharacterId(charId).asLiveData()
    }
}