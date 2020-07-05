package com.thanosfisherman.domain.common

import com.thanosfisherman.domain.model.ErrorModel


sealed class NetworkResultState<out T : Any> {

    object Loading : NetworkResultState<Nothing>()

    data class Success<T : Any>(val data: T) : NetworkResultState<T>()

    data class Error(val error: ErrorModel) : NetworkResultState<Nothing>()

}
