package com.thanosfisherman.presentation.common.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 */
fun singleSourceLiveData(
    scope: CoroutineScope,
    databaseQuery: () -> Flow<DbResultState<List<CharacterModel>>>,
    networkCall: () -> Flow<NetworkResultState<List<CharacterModel>>>,
    saveCallResult: suspend (characters: List<CharacterModel>) -> Unit
): LiveData<DbResultState<List<CharacterModel>>?> {

    return databaseQuery().asLiveData().combineWith(networkCall().asLiveData()) { dbResultState, networkResultState ->
        if (dbResultState is DbResultState.Success) {
            if (networkResultState is NetworkResultState.Success) {
                scope.launch {
                    saveCallResult(networkResultState.data)
                }
            }
            return@combineWith DbResultState.Success(dbResultState.data)
        } else {
            if (networkResultState is NetworkResultState.Success) {
                scope.launch {
                    saveCallResult(networkResultState.data)
                }
                return@combineWith DbResultState.Success(networkResultState.data)
            } else if (networkResultState is NetworkResultState.Error) {
                return@combineWith DbResultState.GenericError
            }
        }

/*        when {
            dbResultState is DbResultState.Success -> {
                return@combineWith DbResultState.Success(dbResultState.data)
            }
            networkResultState is NetworkResultState.Success -> {
                scope.launch {
                    saveCallResult(networkResultState.data)
                }

                return@combineWith DbResultState.Success(networkResultState.data)
            }
            networkResultState is NetworkResultState.Error -> {
                return@combineWith DbResultState.GenericError
            }
        }*/
        return@combineWith dbResultState
    }
}

private fun <ZERO, ONE, RESULT> LiveData<ZERO>.combineWith(liveData: LiveData<ONE>, block: (ZERO?, ONE?) -> RESULT): LiveData<RESULT> {
    val result = MediatorLiveData<RESULT>()

    result.addSource(this) { result.value = block.invoke(this.value, liveData.value) }
    result.addSource(liveData) { result.value = block.invoke(this.value, liveData.value) }
    return result
}