package com.thanosfisherman.data_network.repos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thanosfisherman.data_network.api.MarvelApi
import com.thanosfisherman.domain.model.CharacterModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HeroesPageDataSource(private val marvelApi: MarvelApi) :
    PagingSource<Int, CharacterModel>() {

    private val queryParams = HashMap<String, String>()
    internal fun setQueryParam(key: String, value: String?) {
        if (value.isNullOrBlank()) {
            queryParams.remove(key)
        } else {
            queryParams[key] = value
        }
    }

    internal fun setQueryParam(map: Map<String, String>) {
        map.forEach { (k, v) -> setQueryParam(k, v) }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        val page = params.key ?: 0
        return try {
            val response = marvelApi.getCharacters(page * params.loadSize).asDomain()

            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.isNullOrEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {
        return null
    }
}