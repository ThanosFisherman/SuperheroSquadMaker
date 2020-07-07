package com.thanosfisherman.domain.common

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.CoroutineScope

class HeroesPageDataSourceFactory(private val networkRepo: NetworkRepo, private val scope: CoroutineScope) :
    DataSource.Factory<Int, CharacterModel>() {

    override fun create(): DataSource<Int, CharacterModel> {
        return HeroesPageDataSource(networkRepo, scope)
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE * 3)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()
    }
}