package com.thanosfisherman.domain.common

import androidx.paging.PageKeyedDataSource
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ErrorModel
import com.thanosfisherman.domain.usecase.GetAllCharactersApiUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class HeroesPageDataSource(private val getAllCharactersApiUseCase: GetAllCharactersApiUseCase, private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, CharacterModel>() {

    val networkStateChannel: ConflatedBroadcastChannel<NetworkResultState<List<CharacterModel>>> = ConflatedBroadcastChannel()

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
        scope.launch(getJobErrorHandler() + Dispatchers.IO) {
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
        networkStateChannel.offer(NetworkResultState.Error(ErrorModel.Unknown(e.message.toString())))
    }
}