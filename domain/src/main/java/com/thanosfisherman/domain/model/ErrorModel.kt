package com.thanosfisherman.domain.model

sealed class ErrorModel {

    // Generic network errors
    sealed class NetworkError : ErrorModel() {
        object Network : NetworkError()
        object Timeout : NetworkError()
        object ServerUnavailable : NetworkError()
    }

    // Generic HTTP Error 400+
    data class ServerError(
        val code: String,
        val message: String? = null
    ) : ErrorModel()

    object Unknown : ErrorModel()
}
