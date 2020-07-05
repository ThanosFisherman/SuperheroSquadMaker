package com.thanosfisherman.domain.common

import com.thanosfisherman.domain.model.ErrorModel


sealed class UseCaseResult<out T : Any> {

    object Loading : UseCaseResult<Nothing>()

    data class Success<T : Any>(val data: T) : UseCaseResult<T>()

    data class Error(val error: ErrorModel) : UseCaseResult<Nothing>()

}
