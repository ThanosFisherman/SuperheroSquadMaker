package com.thanosfisherman.domain.common

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.usecase.GetAllCharactersApiUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HeroesPageDataSourceFactory(
    private val heroesPageDataSource: HeroesPageDataSource,
    private val getAllCharactersApiUseCase: GetAllCharactersApiUseCase,
    private val scope: CoroutineScope
) :
    DataSource.Factory<Int, CharacterModel>() {

    override fun create(): DataSource<Int, CharacterModel> {
        return heroesPageDataSource
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