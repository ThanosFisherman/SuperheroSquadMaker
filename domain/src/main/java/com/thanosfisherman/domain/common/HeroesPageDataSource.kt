package com.thanosfisherman.domain.common

import androidx.paging.PageKeyedDataSource
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.NetworkRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class HeroesPageDataSource(private val networkRepo: NetworkRepo, private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, CharacterModel>() {

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
            val response = networkRepo.getAllCharacters(requestedPage * requestedLoadSize)
            response.collect {
                if (it is NetworkResultState.Success) {
                    val results = it.data
                    initialCallback?.onResult(results, null, adjacentPage)
                    callback?.onResult(results, adjacentPage)
                } else {
                    postError("ERROR")
                }
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        // TODO network error handling
        //networkState.postValue(NetworkState.FAILED)
    }
}