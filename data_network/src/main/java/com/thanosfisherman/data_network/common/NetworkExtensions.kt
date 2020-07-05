package com.thanosfisherman.data_network.common

import com.haroldadmin.cnradapter.NetworkResponse
import com.thanosfisherman.domain.common.DomainMappable
import com.thanosfisherman.domain.common.UseCaseResult
import com.thanosfisherman.domain.model.ErrorModel
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T : DomainMappable<R>, U : DomainMappable<ErrorModel>, R : Any> NetworkResponse<T, U>.mapToDomain(): UseCaseResult<R> =
    when (this) {
        is NetworkResponse.Success<T> -> UseCaseResult.Success(this.body.asDomain())
        is NetworkResponse.ServerError -> UseCaseResult.Error(this.body!!.asDomain())
        is NetworkResponse.NetworkError -> UseCaseResult.Error(getError(this.error))
        is NetworkResponse.UnknownError -> UseCaseResult.Error(ErrorModel.Unknown)
    }

fun getError(throwable: Throwable): ErrorModel = when (throwable) {
    is IOException -> ErrorModel.NetworkError.Network
    is ConnectException -> ErrorModel.NetworkError.Network
    is SocketTimeoutException -> ErrorModel.NetworkError.Timeout
    is UnknownHostException -> ErrorModel.NetworkError.ServerUnavailable
    is InterruptedIOException -> ErrorModel.NetworkError.Network
    else -> ErrorModel.Unknown
}