package com.thanosfisherman.domain.common

import androidx.paging.PageKeyedDataSource
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ErrorModel
import com.thanosfisherman.domain.usecase.GetAllCharactersApiUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class HeroesPageDataSource(private val getAllCharactersApiUseCase: GetAllCharactersApiUseCase, private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, CharacterModel>() {

    val networkStateChannel = BroadcastChannel<NetworkResultState<List<CharacterModel>>>(Channel.BUFFERED)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharacterModel>) {
        val numberOfItems = params.requestedLoadSize
        fetchData(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterModel>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        fetchData(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterModel>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        fetchData(page, page - 1, numberOfItems, null, callback)
    }

    private fun fetchData(
        requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        initialCallback: LoadInitialCallback<Int, CharacterModel>?,
        callback: LoadCallback<Int, CharacterModel>?
    ) {
        scope.launch(getJobErrorHandler()) {
            val response = getAllCharactersApiUseCase.execute(requestedPage * requestedLoadSize)
            response.collect {
                when (it) {
                    is NetworkResultState.Success -> {
                        val results = it.data
                        initialCallback?.onResult(results, null, adjacentPage)
                        callback?.onResult(results, adjacentPage)
                    }
                    is NetworkResultState.Loading -> {
                    }
                    else -> {
                    }
                }
                networkStateChannel.offer(it)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        networkStateChannel.offer(NetworkResultState.Error(ErrorModel.Unknown))
    }
}